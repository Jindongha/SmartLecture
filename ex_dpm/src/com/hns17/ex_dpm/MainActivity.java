package com.hns17.ex_dpm;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.provider.Settings.SettingNotFoundException;
import android.widget.Toast;

import com.hns17.ex_dpm.Ex_DPM.DpmClass;

public class MainActivity extends PreferenceActivity implements OnPreferenceClickListener  {
	public DevicePolicyManager devicePolicyManager;
	public ComponentName adminComponent;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        //
      	//mDevice.devicePolicyManager.lockNow();
        
        addPreferencesFromResource(R.xml.pref);
        Preference Reen_Service = (Preference)findPreference("Reen_service");
        Preference Disable_Service = (Preference)findPreference("Disable_service");
        Preference Reen_DPM = (Preference)findPreference("Reen_device_policy");						
        Preference Disable_DPM = (Preference)findPreference("Disable_device_policy");						
        Preference Re_LockNow = (Preference)findPreference("Request_LockNow");						//잠금화면 카테고리
        Preference Re_PassScreen = (Preference)findPreference("Request_PasswordScreen");			//원격잠금 카테고리
        Preference isPass = (Preference)findPreference("Request_isPass");					//원격잠금 카테고리
        
        Reen_Service.setOnPreferenceClickListener(this);
        Disable_Service.setOnPreferenceClickListener(this);
        Reen_DPM.setOnPreferenceClickListener(this);
        Disable_DPM.setOnPreferenceClickListener(this);
        Re_LockNow.setOnPreferenceClickListener(this);
        Re_PassScreen.setOnPreferenceClickListener(this);
        isPass.setOnPreferenceClickListener(this);
          
    }

	public boolean onPreferenceClick(Preference preference) {
        adminComponent = new ComponentName(this, DpmClass.class);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
       
        boolean is_pass=false;
        		
		if(preference.getKey().equals("Reen_device_policy"))
        {
			if (!devicePolicyManager.isAdminActive(adminComponent)){        
	        	Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			   	intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
			   	intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
			   			"Additional text explaining why this needs to be added.");
			   	startActivityForResult(intent, 1);
	        }
			else
				Toast.makeText(this, "이미 기기권한이 사용 중 입니다.", Toast.LENGTH_SHORT).show();
        }
		else if(preference.getKey().equals("Disable_device_policy"))
        {
			if (devicePolicyManager.isAdminActive(adminComponent)){
				Toast.makeText(this, "기기권한이 해제되었습니다.", Toast.LENGTH_SHORT).show();
				devicePolicyManager.removeActiveAdmin(adminComponent);
			}
			else
				Toast.makeText(this, "이미 기기권한이 해제되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
		else if(preference.getKey().equals("Reen_service"))
        {
			startService(new Intent("sms_service"));
        }
		else if(preference.getKey().equals("Disable_service"))
        {
			stopService(new Intent("sms_service"));
        }
		else if(preference.getKey().equals("Request_LockNow"))
        {
			if (devicePolicyManager.isAdminActive(adminComponent))
				devicePolicyManager.lockNow();
			else
				Toast.makeText(this, "기기권한을 설정하셔야 합니다.", Toast.LENGTH_SHORT).show();
        }
		else if(preference.getKey().equals("Request_PasswordScreen"))
        {
			Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
			startActivity(intent);
        }
		else if(preference.getKey().equals("Request_isPass"))
        {
			String PASSWORD_TYPE_KEY = "lockscreen.password_type";
			try {
				final boolean isPattern = 1 ==
						android.provider.Settings.System.getLong(getContentResolver(),
								android.provider.Settings.System.LOCK_PATTERN_ENABLED);
				
				long mode = android.provider.Settings.Secure.getLong(getContentResolver(),
		    			PASSWORD_TYPE_KEY);
		    	final boolean isPassword =
		    			DevicePolicyManager.PASSWORD_QUALITY_NUMERIC == mode
		    			|| DevicePolicyManager.PASSWORD_QUALITY_ALPHABETIC == mode
		    			|| DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC == mode;
		    	is_pass = isPassword || isPattern;
		    	
			} catch (SettingNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(is_pass)
				Toast.makeText(this, "비밀번호 사용", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "비밀번호 미사용", Toast.LENGTH_SHORT).show();			
        }
		
		// TODO Auto-generated method stub
		return false;
	}
    
}
