package org;

import de.inetsoftware.jwebassembly.api.annotation.Export;
import de.inetsoftware.jwebassembly.api.annotation.Import;
import de.inetsoftware.jwebassembly.api.annotation.Replace;
import de.inetsoftware.jwebassembly.api.java.io.ReplacementForFile;
import de.inetsoftware.jwebassembly.api.java.lang.ReplacementForStringCoding;
import de.inetsoftware.jwebassembly.api.java.nio.file.ReplacementForFileSystems;
import de.inetsoftware.jwebassembly.api.java.util.ReplacementForTimeZone;
import de.inetsoftware.jwebassembly.web.DOMString;
import de.inetsoftware.jwebassembly.web.JSObject;
import de.inetsoftware.jwebassembly.web.dom.Document;
import de.inetsoftware.jwebassembly.web.dom.HTMLElement;
import de.inetsoftware.jwebassembly.web.dom.Text;
import de.inetsoftware.jwebassembly.web.dom.Window;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    @Export
    public static void main() {
        Document document = Window.document();
        HTMLElement div = document.createElement("div");
        Text text = document.createTextNode("Hello World, this text come from WebAssembly.");
        div.appendChild( text );
        document.body().appendChild( div );
    }

    @Export
    public static DOMString getStr() {
        return JSObject.domString( "hello");
    }

    @Export
    public static DOMString concatStr(DOMString s) {
        String jStr = JSObject.toJavaString(s);
        DOMString result = JSObject.domString(jStr + " world");
        return result;
    }

    @Export
    public static int add( int a, int b ) {
        return a + b;
    }

    @Export
    public static DOMString[] getDomArr() {
        DOMString[] result = new DOMString[10];
        result[0] = JSObject.domString("first");
        result[2] = JSObject.domString("third");
        result[9] = JSObject.domString("tenth");
        return result;
    }

    @Export
    public static List<DOMString> getDomList() {
        List<DOMString> result = new ArrayList<>();
        result.add(JSObject.domString("abc"));
        result.add(JSObject.domString("def"));
        return result;
    }

    // This will put {modu: f } on the wasmImport in the *.wasm.js file
    // You can then use modu() from java, and it will return "hello"
    @Import( module = "modu", name = "f", js="() => {return \"hello\";}" )
    static native String modu();

    @Export
    public static String getModu() {
        return modu();// returns "hello" when called from the JS
    }

    //@Import(module = "repl", name = "toChar", js="")
    //static native void k


    // Replace the String.replaceAll method
    @Replace( "java/lang/String.replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;" )
    @Import( module = "strRepl", name = "replaceAll",
        js="(s, p1, p2) => { " +
            "s = wasmImports.Web.fromChars(s[2]); " +
            "p1 = wasmImports.Web.fromChars(p1[2]); " +
            "p2 = wasmImports.Web.fromChars(p2[2]);" +
            "let reg = RegExp(p1);" +
            "return s.replace(reg, p2);" +
        "}")
    static native String nameDontMatter( String x , String y);

    @Export
    public static String testReplaceAll(DOMString s, DOMString p1, DOMString p2) throws Exception {
        String sStr = JSObject.toJavaString(s);
        String p1Str = JSObject.toJavaString(p1);
        String p2Str = JSObject.toJavaString(p2);
        return sStr.replaceAll(p1Str, p2Str);
    }

    // Replace the String.toLowerCase method
    @Replace( "java/lang/String.toLowerCase()Ljava/lang/String;" )
    @Import( module = "strRepl", name = "toLower",
        js="(s) => { " +
            "console.log(s);" +
            "s = wasmImports.Web.fromChars(s[2]);" +
            "return s.toLowerCase();" +
        "}")
    static native String nameDontMatter2(String nameDontMatter);

    @Export
    public static String testToLowerCase(DOMString s) {
        String sStr = JSObject.toJavaString(s);
        return sStr.toLowerCase();
    }

    // Replace String.split
    @Replace( "java/lang/String.split(Ljava/lang/String;)[Ljava/lang/String;" )
    @Import( module = "strSplit", name = "split",
        js="(s, p1) => { " +
            "console.log(s);" +
            "s = wasmImports.Web.fromChars(s[2]);" +
            "p1 = wasmImports.Web.fromChars(p1[2]); " +
            "let reg = RegExp(p1);" +
            "return s.split(reg);" +
        "}")
    static native String nameDontMatter3(String nameDontMatter);
    @Export
    public static String[] testSplit(DOMString s, DOMString p1) throws Exception {
        String sStr = JSObject.toJavaString(s);
        String p1Str = JSObject.toJavaString(p1);
        return sStr.split(p1Str);
    }
}
