## 多项目工程入门演示例子
## 执行任务
1. 执行 `gradle distanceToIceberg` 结果
```
:bluewhale:distanceToIceberg
20 nautical miles
:krill:distanceToIceberg
5 nautical miles

BUILD SUCCESSFUL in 0s
2 actionable tasks: 2 executed
```
> 在执行上面的任务的时候, gradle 会自动在所有的子项目中查找相应名称的任务执行。
> 也可以使用绝对路劲的方式执行, 例如: `gradle -q :hello :krill:hello hello`


