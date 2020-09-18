# AutomaticInjection_PushCode
主要是为了解决推送给状态码对应巨多 switch case,太耦合问题

核心就是通过kapt， 对应状态码，实行依赖注入效果。

只需要定义顶层的 抽象类统一行为，就可以值关注自己根据code码的业务需求了，



后续：
  1.可进行code 检测/n
  2.code 对应文档集中输出/n
  3.重定向操作/n
