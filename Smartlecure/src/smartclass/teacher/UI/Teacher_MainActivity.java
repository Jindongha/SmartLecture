package smartclass.teacher.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class Teacher_MainActivity extends Activity implements OnClickListener {
	Button lock_button;
	Button student_info_button;
	Button QNA_button;
	Button account_button;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.teacher_main_layout);
        
        lock_button = (Button)findViewById(R.id.lock_button);
        student_info_button = (Button)findViewById(R.id.student_button);
        QNA_button = (Button)findViewById(R.id.qna_button);
        account_button = (Button)findViewById(R.id.account_button);
        
        lock_button.setOnClickListener(this);
        student_info_button.setOnClickListener(this);
        QNA_button.setOnClickListener(this);
        account_button.setOnClickListener(this);
          
    }

	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		case R.id.lock_button:
		
			intent = new Intent(getBaseContext(),Teacher_Lock_Activity.class);
			startActivity(intent);
			break;
			
		case R.id.student_button:
			
			intent = new Intent(getBaseContext(),Teacher_StudentInfo_Activity.class);
			startActivity(intent);
			break;
		case R.id.qna_button:
			
			intent = new Intent(getBaseContext(),Teacher_QNA_Activity.class);
			startActivity(intent);
			break;
		case R.id.account_button:
			
			intent = new Intent(getBaseContext(),Teacher_Account_Activity.class);
			startActivity(intent);
			break;
		
		
		};
		
	}

}
