package corey.cpitzoapp.todolist;


	import corey.cpitzoapp.todolist.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

	public class UpdateActivity extends Activity{
			private TextView tvName;
			private TextView tvClass;
			private TextView tvDue;
			
			@Override
				protected void onCreate(Bundle savedInstanceState){
				super.onCreate(savedInstanceState);
				setContentView(R.layout.update_view);
				
				tvName = (TextView)findViewById(R.id.nameEdit);
				tvDue = (TextView)findViewById(R.id.dueEdit);
				tvClass = (TextView)findViewById(R.id.classEdit);
				
				Intent updateIntent = this.getIntent();
				String[] defaultTexts = updateIntent.getExtras().getStringArray("itemContents");
				tvName.setText(defaultTexts[0]);
				tvDue.setText(defaultTexts[1]);
				tvClass.setText(defaultTexts[2]);
				
			}
			
			public void updateDB(View v){
				Intent returnIntent = new Intent();
				String[] updateResults = { tvName.getText().toString(),tvDue.getText().toString(),
						tvClass.getText().toString()};
				returnIntent.putExtra("results",updateResults);
				setResult(RESULT_OK, returnIntent);
					this.finish();
				}
			



}
