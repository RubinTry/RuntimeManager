package cn.rubintry.runtimemanager;

import android.widget.Toast;

import androidx.annotation.NonNull;

import cn.rubintry.rtmanager.OnRuntimeChangeListener;
import cn.rubintry.rtmanager.core.RuntimeManager;
import cn.rubintry.rtmanager.RuntimeType;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RuntimeManager.with(this)
                .addProductUrl("https://baidu.com/")
                .addTestUrl("http://weacar.com/")
                .addRuntimeChangeListener(new OnRuntimeChangeListener() {
                    @Override
                    public void onChange(@NonNull String newHost, @NonNull RuntimeType runtimeType) {
                        if(runtimeType == RuntimeType.CUSTOM){
                            Toast.makeText(Application.this  , "已切换到自定义环境", Toast.LENGTH_SHORT).show();
                        }else if(runtimeType == RuntimeType.PRODUCT){
                            Toast.makeText(Application.this  , "已切换到生产环境", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Application.this  , "已切换到测试环境", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .install();
    }
}
