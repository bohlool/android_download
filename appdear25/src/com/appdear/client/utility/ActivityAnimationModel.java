package com.appdear.client.utility;

import android.app.Activity;

/**
 * ���ã���ֹ����ڵͰ汾�������г��֣�VerifyError����
 * @author zxy
 *
 */
public class ActivityAnimationModel {   
    private Activity context;
    
    public ActivityAnimationModel(Activity context){   
        this.context = context;   
    }
    
    /**  
     * call overridePendingTransition() on the supplied Activity.  
     * @param a   
     * @param b  
     */   
    public void overridePendingTransition(int a, int b){   
        context.overridePendingTransition(a, b);   
    }   
}  