package corey.cpitzoapp.todolist;


import corey.cpitzoapp.todolist.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InsertActivity  extends Activity{
		private TextView tvName;
		private TextView tvClass;
		private TextView tvDue;
		
		@Override
			protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.insert);
			tvName = (TextView)findViewById(R.id.nameEdit);
			tvDue = (TextView)findViewById(R.id.dueEdit);
			tvClass = (TextView)findViewById(R.id.classEdit);
		}
		
		public void insertDB(View v){
			Intent returnIntent = new Intent();
			
			String[] insertResults = { tvName.getText().toString(),tvDue.getText().toString(),
					tvClass.getText().toString()};
			returnIntent.putExtra("results",insertResults);
			setResult(RESULT_OK, returnIntent);
				this.finish();
			}
		



}
