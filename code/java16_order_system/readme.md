# 关于前端实现的说明

目前前端代码存在两份. 

第一份是课堂上的, 只包含 admin.html(管理员页) 和 index.html(用户页)

这份代码中使用了 Vuetify, 而且所有代码都在一个页面中, 比较复杂, 不好理解. 



第二份代码是老湿今天新搞的, 去掉了 Vuetify, 纯 html+css 手工布局, 并且对功能进行了拆分, 分成了多个页面. 

目前文件路径结构如下: `java16_order_system\src\main\webapp`

```
├── admin-dish.html
├── admin-order.html
├── css
│   └── style.css
├── js
│   ├── admin-dish.js
│   ├── admin-order.js
│   ├── user-dish.js
│   └── user-order.js
├── login.html
├── register.html
├── user-dish.html
└── user-order.html
```

其中, login.html 和 register.html 分别为登陆和注册页面, 逻辑比较简单, 就把 CSS 和 JS 都写到一起了. 

* user-dish.html 为普通用户点餐页面, 配套 js 为 user-dish.js
* user-order.html 为普通用户查看订单页面, 配套 js 为 user-order.js
* admin-dish.html 为管理员管理菜品页面, 配套 js 为 admin-dish.js
* admin-order.html 为管理员查看订单页面, 配套 js 为 admin-order.js
* style.css 为样式文件, 包含了这四个页面的样式内容. 



> 关于删除菜品失败的说明: 
>
> 失败原因是因为 order_dish 表作为 dishes 表的外键约束导致的. 
>
> 这里应该是最初设计项目的时候考虑的不够周全. 更科学的做法应该是去除外键约束, 然后在 order_dish 表中
>
> 同时记录菜品的名称和当时的价格. 
>
> 因为菜品的情况可能一直在变, 而订单一旦下了就不变了. 订单应该记录的是当时下单那个时刻的情况, 而不应该受到 dishes 表的变动而造成影响. 