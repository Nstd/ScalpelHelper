ScalpelHelper
--------------------
![current version](https://img.shields.io/badge/Current%20Version-0.1-brightgreen.svg)
![minSdkVersion](https://img.shields.io/badge/minSdkVersion-14-blue.svg)

![demo](https://github.com/Nstd/ScalpelHelper/blob/master/screenshots/sample.gif)

## Usage

### Gradle
``` gradle
compile 'com.github.nstd:scalpelhelper:0.1'
```

### Java
Override setContentView() to inject scalpel

``` java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        
        new ScalpelHelper().injectScalpel(this);
    }
}
```

## Version

### v0.1
fix dataBinding crash

### v0.0.2
change minSDK to 14

### v0.0.1
init

## Thanks
* [Scalpel - Jake Worthon](https://github.com/JakeWharton/scalpel)
* [FloatingMenu - rjsvieira](https://github.com/rjsvieira/floatingMenu)
* [icon](http://www.iconfont.cn/collections/detail?spm=a313x.7781069.1998910419.d9df05512&cid=3191)
