///////////////////////////////////////////////////////
// 此处的 parser.cc 会编译成一个可执行程序. 
// 运行之后就会进行 html 的预处理过程. 
///////////////////////////////////////////////////////

#include <string>
#include <vector>
#include <iostream>
#include <fstream>

#include <boost/filesystem/path.hpp>
#include <boost/filesystem/operations.hpp>

#include "../common/Util.hpp"

using namespace std;

// 设置一些全局的配置项. 对应到一些目录位置
string g_input_path = "../data/input";
// 最终的输出是一个行文本文件. 
string g_output_path = "../data/tmp/raw_input";

// 为了进行解析工作, 准备一个结构体, 表示一个文档. 
struct DocInfo {
  // 文档标题. 来自于 html 的 文件名. 最终展示到搜索结果中的标题
  string title;
  // 该文档对应的在线版本的文档地址. 落地页的地址(点击 url). 
  string url;
  // 进行去标签之后得到的 html 内容. 
  string content;
};

bool enumFile(const string& input, vector<string>* output) {
  // C++ 17 之前, 标准库中, 是无法遍历目录的. 
  // 只能借助操作系统的 api 或者使用第三方库(boost)
  // 给 boost::filesystem 起了个简短的别名. 
  namespace fs = boost::filesystem;

  fs::path rootPath(input);
  // 判断该目录是否存在. 
  if (!fs::exists(rootPath)) {
    cout << "input path not exists!" << endl;
    return false;
  }

  // 遍历目录. 
  // 输入目录中, 还包含了一些其他的子目录, 子目录中可能还有子目录. 
  // 递归. boost 中有现成的方式, 不必写递归代码, 就能完成递归工作. 
  fs::recursive_directory_iterator endIter; // 默认构造函数得到迭代器的结束位置. 
  for (fs::recursive_directory_iterator it(rootPath); it != endIter; ++it) {
    // 此处再进行一些判定
    // 1. 是不是普通文件. 如果不是, 直接过滤
    // 2. 是不是 .html 后缀的文件, 如果不是, 直接过滤
    if (!fs::is_regular_file(*it)) {
      continue;
    }
    if (it->path().extension() != ".html") {
      continue;
    }
    // 得到的结果就是 html 文件的路径
    output->push_back(it->path().string());
  }

  return true;
}

bool parseTitle(const string& html, string* title) {
  // 根据 html 中的 title 标签, 找到标题
  size_t beg = html.find("<title>");
  if (beg == string::npos) {
    cout << "parseTitle failed! no beg!" << endl;
    return false;
  }
  size_t end = html.find("</title>");
  if (end == string::npos) {
    cout << "parseTitle failed! no end!" << endl;
    return false;
  }
  // beg 指向的位置是 <title> 的 < , 需要往后移动几步
  beg += string("<title>").size();
  *title = html.substr(beg, end - beg);
  return true;
}

// 离线文档路径形如
// ../data/input/html/foreach.html
// 想要得到的在线文档路径形如:
// https://www.boost.org/doc/libs/1_53_0/doc/html/foreach.html
// 基于离线文档路径进行一个简单的字符串变换即可. 
bool parseUrl(const string& path, string* url) {
  string head = "https://www.boost.org/doc/libs/1_53_0/doc/";
  string tail = path.substr(g_input_path.size());
  *url = head + tail;
  return true;
}

bool parseContent(const string& html, string* content) {
  bool isContent = true;
  // 遍历 html 中的每个字符
  for (auto c : html) {
    if (isContent) {
      // 当前在操作正文
      if (c == '<') {
        // 接下来要切换到标签模式. 
        isContent = false;
      } else {
        // 还需要把 html 中的 \n 干掉. 否则行文本就不好使了. 
        if (c == '\n') {
          c = ' ';
        }
        // 接下来操作的还是正文. 就把正文内容写入到最终结果中. 
        content->push_back(c);
      }
    } else {
      // 当前在操作标签
      // 如果现在的字符是 > 切换回正文状态. 否则, 其他标签内部的内容统统忽略
      if (c == '>') {
        isContent = true;
      }
    }
  }
  return true;
}

bool parseFile(const string& input, DocInfo* docInfo) {
  // 整个解析分成三个环节
  // 0. 读取出文件的内容
  string html;
  // 这个函数是一个功能比较底层的函数, 可以放到 common 目录中
  bool ret = common::Util::read(input, &html); // 根据路径直接读取出整个文件的内容
  if (!ret) {
    cout << "read file failed! " << input << endl;
    return false;
  }
  // 1. 解析文档的标题(文件名)
  ret = parseTitle(html, &docInfo->title);
  if (!ret) {
    cout << "parseTitle failed! " << input << endl;
    return false;
  }
  // 2. 解析文档的url(文件路径)
  ret = parseUrl(input, &docInfo->url);
  if (!ret) {
    cout << "parseUrl failed! " << input << endl;
    return false;
  }
  // 3. 解析文档的正文(去 html 标签)
  ret = parseContent(html, &docInfo->content);
  if (!ret) {
    cout << "parseContent failed! " << input << endl;
    return false;
  }
  return true;
}

void writeOutput(const DocInfo& docInfo, ofstream& outputFile) {
  outputFile << docInfo.title << "\3" << docInfo.url << "\3" << docInfo.content << endl;
}

int main() {
  // 1. 枚举出 input 目录中的所有文件路径, 以便后续进行打开和操作. 
  vector<string> fileList;
  if (!enumFile(g_input_path, &fileList)) {
    cout << "enumFile failed" << endl;
    return 1;
  }
  ofstream outputFile(g_output_path);
  if (!outputFile.is_open()) {
    cout << "open output file failed" << endl;
    return 1;
  }
  for (const auto& f : fileList) {
    // 2. 依次解析每个文档结构, 并进行去标签操作. 
    // 这个对象用来表示最终的输出文件. 
    // 把这个结果放到这里, 就可以看到文件遍历的结果了. 
    cout << "parse file: " << f << endl;
    // 针对该文件需要进行解析
    DocInfo docInfo;
    if (!parseFile(f, &docInfo)) {
      cout << "parseFile failed: " << f << endl;
      continue;
    }
    // 3. 把结果整理成一个最终的行文本文件. 
    writeOutput(docInfo, outputFile);
  }
  outputFile.close();
  
  return 0;
}
