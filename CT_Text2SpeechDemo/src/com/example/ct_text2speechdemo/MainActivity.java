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
