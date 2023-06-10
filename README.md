# JinusicBox
整活的东西

## 起因
* 为了方便大家在不张嘴的情况下，快速说话
* 为了给高中同学留下~~美好~~回忆

## 版本规划
* v1.4e 完成所有按键编码
* v1.5a DLC，兑换码大更新
* v1.6a 活字乱刷术
* **v1.7a 局域网联机功能**

## 未解决bug
```java
>for (musicButton mb : musicButtons) {
>  mb.getButton().setOnClickListener(view -> 
>    mb.getButton().startAnimation(anim););
>  }
>//这会引起一些神秘的动画bug
>//当多个按键同时点击，并且在动画未播完时点击其他按键
>//就可以在不点击的情况下触发动画
```
