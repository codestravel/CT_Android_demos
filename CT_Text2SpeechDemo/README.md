## Android技术分享-文字转语音并朗读

最近在做一个项目，其中有一个功能是需要将文本转换成语音并播放出来。下面我将我的做法分享一下。

非常令人开心的是，Android系统目前已经集成了TTS，提供了相关的库供我们进行调用，不必到处去搜寻第三方库，直接导入android.speech.tts.TextToSpeech即可。
```java
//导入TTS的包
import android.speech.tts.TextToSpeech;  

//定义一个tts对象
private TextToSpeech tts;
```

其次，要想实例化这个对象需要两个参数，一个是Context对象,另一个是TextToSpeech类对应的监听器对象：OnLnitListener对象。一般Context对象传入当前的Activity，OnLnitListener可以自己写类继承，并实现其方法。

```java
//导入监听包
import android.speech.tts.TextToSpeech.OnInitListener;  

//初始化tts监听对象
tts = new TextToSpeech(this, OnInitListener);  
```

OnLnitListener接口中只要是onInit方法，其功能是对tts对象进行初始化，设置一下语言，判断文字是否转换成功以及当前系统是否支持该语言。

```java
@Override  
public void onInit(int status){  
    // 判断是否转化成功  
    if (status == TextToSpeech.SUCCESS){  
        //默认设定语言为中文，原生的android貌似不支持中文。
        int result = tts.setLanguage(Locale.CHINESE);  
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){  
            Toast.makeText(MainActivity.this, R.string.notAvailable, Toast.LENGTH_SHORT).show();  
        }else{
            //不支持中文就将语言设置为英文
            tts.setLanguage(Locale.US);
        }  
    }  
}  
```

最后，只要在合适的时候调用tts转文字到语音的方法即可.

```java
tts.speak("需要转化的文字", TextToSpeech.QUEUE_FLUSH, null);  
```

下面是写的一个demo：

MainActivity.java:
```java
/**
 * Author: sandy
 * QQ技术交流群：439261058
 * 微信公众号：代码之间(codestravel)
**/
package com.example.ct_text2speechdemo;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnInitListener{
	//定义控件
	private Button speechButton;  
    private TextView speechText;  
    private TextToSpeech tts;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化TTS
		tts = new TextToSpeech(this, this);
		//获取控件
		speechText = (TextView)findViewById(R.id.speechTextView);  
        speechButton = (Button)findViewById(R.id.speechButton);  
        //为button添加监听
        speechButton.setOnClickListener(new OnClickListener(){   
            @Override  
            public void onClick(View v){  
                // TODO Auto-generated method stub  
                tts.speak(speechText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);  
            }  
        });  
		
	}
    
    @Override  
    public void onInit(int status){  
        // 判断是否转化成功  
        if (status == TextToSpeech.SUCCESS){  
            //默认设定语言为中文，原生的android貌似不支持中文。
            int result = tts.setLanguage(Locale.CHINESE);  
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){  
                Toast.makeText(MainActivity.this, R.string.notAvailable, Toast.LENGTH_SHORT).show();  
            }else{
                //不支持中文就将语言设置为英文
                tts.setLanguage(Locale.US);
            }  
        }  
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

```
完整代码参见[github](完整代码参见[github](https://github.com/codestravel/CT_Android_demos/tree/master/CT_Text2SpeechDemo))

