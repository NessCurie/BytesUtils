## BytesUtils
1. 大小端byte数组和各种数据类型的互转的工具类EndianUtils;
2. 参照ArrayList实现一个能添加任意数据类型的ByteArrayList;
3. 将Java的byte数组分段解析为所需数据的工具类BytesReader;
4. 使用树结构的方式对Java的byte数组进行快速解析和构建;

For more information please see: 
[大小端byte数组和各种数据类型的互转](https://nesscurie.github.io/2018/07/21/byte%E6%95%B0%E7%BB%84%E5%BC%8F%E5%8D%8F%E8%AE%AE%E8%A7%A3%E6%9E%90/%E5%A4%A7%E5%B0%8F%E7%AB%AFbyte%E6%95%B0%E7%BB%84%E5%92%8C%E5%90%84%E7%A7%8D%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B%E7%9A%84%E4%BA%92%E8%BD%AC/)
[参照ArrayList实现一个能添加任意数据类型的ByteArrayList](https://nesscurie.github.io/2018/07/25/byte%E6%95%B0%E7%BB%84%E5%BC%8F%E5%8D%8F%E8%AE%AE%E8%A7%A3%E6%9E%90/%E5%8F%82%E7%85%A7ArrayList%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E8%83%BD%E6%B7%BB%E5%8A%A0%E4%BB%BB%E6%84%8F%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B%E7%9A%84ByteArrayList/)
[将Java的byte数组分段解析为所需数据的工具类BytesReader](https://nesscurie.github.io/2018/07/28/byte%E6%95%B0%E7%BB%84%E5%BC%8F%E5%8D%8F%E8%AE%AE%E8%A7%A3%E6%9E%90/%E5%B0%86Java%E7%9A%84byte%E6%95%B0%E7%BB%84%E5%88%86%E6%AE%B5%E8%A7%A3%E6%9E%90%E4%B8%BA%E6%89%80%E9%9C%80%E6%95%B0%E6%8D%AE%E7%9A%84%E5%B7%A5%E5%85%B7%E7%B1%BBBytesReader/)
[使用树结构的方式对Java的byte数组进行快速解析和构建](https://nesscurie.github.io/2018/08/07/byte%E6%95%B0%E7%BB%84%E5%BC%8F%E5%8D%8F%E8%AE%AE%E8%A7%A3%E6%9E%90/%E4%BD%BF%E7%94%A8%E6%A0%91%E7%BB%93%E6%9E%84%E7%9A%84%E6%96%B9%E5%BC%8F%E5%AF%B9Java%E7%9A%84byte%E6%95%B0%E7%BB%84%E8%BF%9B%E8%A1%8C%E5%BF%AB%E9%80%9F%E8%A7%A3%E6%9E%90%E5%92%8C%E6%9E%84%E5%BB%BA/)

## Download
Add this in your root build.gradle file (not your module build.gradle file):
<pre><code>allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
</code></pre>

Then, add the library to your module build.gradle
<pre><code>dependencies {
    compile 'com.github.NessCurie:BytesUtils:latest.release.here'
}
</code></pre>

such as release is 1.0

you can use:
<pre><code>dependencies {
    compile 'com.github.NessCurie:RecyclerViewUtils:1.0'
}
</code></pre>