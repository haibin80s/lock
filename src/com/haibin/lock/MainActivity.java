package com.haibin.lock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
	private DevicePolicyManager policyManager;
	private ComponentName componentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, LockReceiver.class);
		if (policyManager.isAdminActive(componentName)) {
			lock();
		} else {
			activeManager();
		}
		setContentView(R.layout.main);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lock();
	}

	private void activeManager() {
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Ò»¼üËøÆÁ");
		startActivity(intent);
	}
	
	@SuppressWarnings("deprecation")
	private void lock(){
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock kl = km.newKeyguardLock(getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

                if (kl != null) {
                        kl.disableKeyguard();
                }

                policyManager.lockNow();

                if (kl != null) {
                        kl.reenableKeyguard();
                }
        }
        
        finish();
		
//        new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);  
//				WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK 
//                        | PowerManager.ACQUIRE_CAUSES_WAKEUP , "SimpleTimer");  
//				mWakelock.acquire();  
////				mWakelock.release();
//			}
//		}, 1000);
		
	}

}
