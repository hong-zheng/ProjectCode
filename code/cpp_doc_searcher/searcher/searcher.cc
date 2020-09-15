#include <iostream>
#include <fstream>
#include <algorithm>
#include <jsoncpp/json/json.h>

// 这个头文件在前面的 Util.hpp 中为了使用 split 已经被包含过了. 
// #include <boost/algorithm/string.hpp>
#include "../common/Util.hpp"
#include "searcher.h"

using namespace std;

namespace searcher {

///////////////////////////////////////////////////////
// Index 模块的相关内容
///////////////////////////////////////////////////////

const char* const DICT_PATH = "../jieba_dict/jieba.dict.utf8";    
const char* const HMM_PATH = "../jieba_dict/hmm_model.utf8";    
const char* const USER_DICT_PATH = "../jieba_dict/user.dict.utf8";    
const char* const IDF_PATH = "../jieba_dict/idf.utf8";
const char* const STOP_WORD_PATH = "../jieba_dict/stop_words.utf8";

// 专门写一个 Index 类的构造函数, 来初始化 jieba 对象
Index::Index() : jieba(DICT_PATH,
        HMM_PATH,    
        USER_DICT_PATH,    
        IDF_PATH,    
        STOP_WORD_PATH) {
}

// 把 docId 就作为数组下标. 
const DocInfo* Index::getDocInfo(int64_t docId) {
  if (docId >= (int64_t)forwardIndex.size()) {
    return nullptr;
  }
  return &forwardIndex[docId];
}

const InvertedList* Index::getInverted(const string& key) {
  // auto => unordered_map<string, InvertedList>::iterator
  auto it = invertedIndex.find(key);
  if (it == invertedIndex.end()) {
    // 没找到这个 key 对应的倒排拉链
    return nullptr;
  }
  return &it->second;
}

// 这个函数就比较复杂了. 
// 输入内容是前面准备好的 raw_input 文件. 
// raw_input 是一个行文本文件. 
// 每行记录对应到一个文档. 
// 每行包含三个部分, 使用 \3 分割. 
// 分别是 title, url, content
bool Index::build(const string& inputPath) {
  cout << "build start" << endl;
  // 1. 按行读取文件内容. 
  ifstream file(inputPath.c_str());
  if (!file.is_open()) {
    cout << "build open inputPath failed! inputPath = " << inputPath << endl;
    return false;
  }
  string line;
  while (getline(file, line)) {
    // 2. 根据当前行, 构造 DocInfo 对象并插入到正排索引中. 
    DocInfo* docInfo = buildForward(line);
    if (docInfo == nullptr) {
      continue;
    }
    // 3. 再进一步解析 DocInfo 中的内容, 把结构构造到倒排索引结构中. 
    buildInverted(*docInfo);

    // 最好在这里输出日志, 就可以很明显的看到当前索引构造的进度了. 
    // 但是也最好不要每次循环都打印, 打印内容太多了, 影响程序效率. 
    // 这里可以想个办法, 让日志既不会打印太多, 也能让用户看到构建的进度. 
    // 每隔 100 个 docInfo 对象打印一条日志. 
    if (docInfo->docId % 100 == 0) {
      cout << "Index Build docId: " << docInfo->docId << endl;
    }
  }

  file.close();
  cout << "build finish" << endl;
  return true;
}

DocInfo* Index::buildForward(const std::string& line) {
  // 1. 按照 \3 针对这一行数据进行切分
  //    切分出的结果第一部分就是标题, 第二部分就是 url, 第三部分就是正文. 
  vector<string> tokens;
  common::Util::split(line, "\3", &tokens);
  if (tokens.size() != 3) {
    return nullptr;
  }
  // 2. 创建 DocInfo 对象, 把这些结构都拼装进去. 
  DocInfo docInfo;
  docInfo.docId = forwardIndex.size();
  docInfo.title = tokens[0];
  docInfo.url = tokens[1];
  docInfo.content = tokens[2];
  // 这样写, 会把 docInfo 对象拷贝一份
  // forwardIndex.push_back(docInfo);
  forwardIndex.push_back(std::move(docInfo));
  // 3. 把得到的 DocInfo 对象的指针返回回去, 供倒排索引的构造过程来使用. 
  // return &docInfo;
  return &forwardIndex.back();
}

// 通过这个函数来构造倒排索引
void Index::buildInverted(const DocInfo& docInfo) {
  struct WordCnt {
    int titleCnt;  // 标题中出现的次数
    int contentCnt;// 正文中出现的次数

    // 这个默认构造函数很重要. 防止后面构造 WordCnt 的时候没有把属性都填成 0. 
    WordCnt() : titleCnt(0), contentCnt(0) {}
  };
  // 使用这个 hash 表进行统计词频
  unordered_map<string, WordCnt> wordCntMap;

  // 1. 先针对文档标题进行分词
  vector<string> titleTokens;
  cutWord(docInfo.title, &titleTokens);
  // 2. 根据分词结果, 统计每个词在该标题中的出现次数. 
  for (string word : titleTokens) {
    // 注意!!! 此处得到 word 是一个单词. 这个单词是否要区分它的大小写呢? 
    // 区分指的是: Hello 和 hello 和 HELLO 认为是三个单词, 分别出现 1 次. 
    // 不区分指的是: Hello hello HELLO 认为是一个单词, 出现 3 次. 
    // 此处参考搜狗搜索, 是没有区分大小写的. 
    // 想不区分大小写, 就需要在制作倒排的时候把每个词都统一转成全大写或者全小写. 
    boost::to_lower(word);
    ++wordCntMap[word].titleCnt;
  }
  // 3. 再针对文档正文进行分词
  vector<string> contentTokens;
  cutWord(docInfo.content, &contentTokens);
  // 4. 根据分词结果, 统计每个词在该正文中的出现次数. 
  for (string word : contentTokens) {
    boost::to_lower(word);
    ++wordCntMap[word].contentCnt;
  }
  // 5. 遍历统计结果(key 是词, value 就是 标题次数 和 正文次数),
  // 根据统计结果来更新倒排索引. 
  //    根据词找到倒排中的对应的倒排拉链. 
  //构造 Weight 对象, 把这个 Weight 插入到拉链中即可. 
  //    此处 auto 得到的参数是一个 std::pair
  for (const auto& wordPair : wordCntMap) {
    Weight weight;
    weight.docId = docInfo.docId;
    // 权重的算法: 标题中出现的次数 * 10 + 正文中出现的次数. 
    weight.weight = wordPair.second.titleCnt * 10 + wordPair.second.contentCnt;
    // 也可以把这个词往 weight 对象中也存一份, 以备后用
    weight.word = wordPair.first;
    // weight 对象准备好了, 接下来就可以更新倒排拉链了. 
    // 根据当前的词, 在倒排索引中找到对应的倒排拉链. 
    InvertedList& invertedList = invertedIndex[wordPair.first];
    invertedList.push_back(weight);
  }
}

void Index::cutWord(const string& word, vector<string>* tokens) {
  jieba.CutForSearch(word, *tokens);
}

///////////////////////////////////////////////////////
// 以下是 Searcher 模块的实现
///////////////////////////////////////////////////////

bool Searcher::init(const std::string& inputPath) {
  return index->build(inputPath);
}

bool Searcher::search(const std::string query, std::string* output) {
  // 这个函数就对应到搜索功能的核心流程. 
  // 1. [分词] 对查询词进行分词操作
  vector<string> tokens;
  index->cutWord(query, &tokens);
  // 2. [触发] 根据分词结果, 查倒排, 找到相关的文档id
  vector<Weight> allTokenResult;
  for (string word : tokens) {
    // 查倒排之前, 记得要忽略大小写(统一转成小写)
    boost::to_lower(word);
    const auto* invertedList = index->getInverted(word);
    if (invertedList == nullptr) {
      // 该词在所有的文档中都不存在. 
      // 也没找到倒排拉链. 
      continue;
    }
    // 如果倒排拉链的结果不是空着的, 就可以把这些结果合并到一个大的 数组 中. 
    // 以备下一步进行排序. 分词结果可能是多个. 对应的倒排拉链也有 N 条
    // 需要把 N 条拉链合并成一个大的拉链(数组). 然后再针对数组进行排序. 
    allTokenResult.insert(allTokenResult.end(), invertedList->begin
      (), invertedList->end());
  }
  // 3. [排序] 根据该词在该文档中的权重, 对结果进行排序. 
  //    按照权重值进行降序排序, 需要在调用 sort 的时候指定排序规则
  std::sort(allTokenResult.begin(), allTokenResult.end(), 
      [](const Weight& w1, const Weight& w2){
        // 如果返回值为 true, w1 就排在 w2 前面. 如果是 false 就排在后面. 
        // 按照权重降序排序
        return w1.weight > w2.weight;
        // 按照权重升序排序
        // return w1.weight < w2.weight;
      });
  // 4. [构造结果] 把最终的结果进行查正排, 构造成 JSON 格式的数据.  
  //    为了把结果构造成 json 格式的字符串, 就需要使用 JSON 的库. jsoncpp
  //    Json::Value 这个类, 既可以当成 vector , 也能当成一个 map 来使用. 
  //    results 是最终的搜索结果, 里面有 N 条记录, results 就相当于一个数组
  Json::Value results;
  for (const auto& weight : allTokenResult) {
    // weight 中只包含 docId, 咱们需要根据 docId 在正排索引中查找. 
    // 查到相关的内容, 再构造成 JSON 格式的数据
    const auto* docInfo = index->getDocInfo(weight.docId);
    Json::Value result;
    result["title"] = docInfo->title;
    result["url"] = docInfo->url;
    result["desc"] = generateDesc(docInfo->content, weight.word);
    results.append(result);
  }
  // 需要把 Json::Value 对象转成字符串, 写入到 output 这个输出参数中. 
  // 使用 Writer 类来把 Json::Value 转成 string
  // 如果要是想把 string 解析回 Json::Value, 使用 Reader 类. 
  Json::FastWriter writer;
  *output = writer.write(results);
  return true;
}

string Searcher::generateDesc(const string& content, const string& word) {
  // 生成描述信息. 
  // 把正文中包含 word 的片段摘取出一部分来, 作为返回结果. 
  // 1. 先找到 word 在 content 中的位置. 
  int64_t pos = content.find(word);
  if (pos == (int64_t)string::npos) {
    // 词在正文中不存在, 此时描述就直接返回一个空串. 
    // 这种情况一般不会出现. (词在标题中存在, 但是正文中不存在)
    return "";
  }
  // 2. 以该 word 出现位置为中心, 往前找 60 个字符, 以这个位置为基准, 再往后找 160 个字符. 
  //    需要注意处理边界条件. 
  int64_t beg = pos < 60 ? 0 : pos - 60;
  if (beg + 160 >= (int64_t)content.size()) {
    // 词出现在正文末尾. 少截点内容
    return content.substr(beg);
  } else {
    string desc = content.substr(beg, 160);
    desc[desc.size() - 1] = '.';
    desc[desc.size() - 2] = '.';
    desc[desc.size() - 3] = '.';
    return desc;
  }
}

}  // end searcher
