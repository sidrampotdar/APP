package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText codeEditText;
    private Button compileButton;
    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeEditText = findViewById(R.id.codeEditText);
        compileButton = findViewById(R.id.compileButton);
        outputTextView = findViewById(R.id.outputTextView);

        compileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceCode = codeEditText.getText().toString();
                JavaCompilerApp.compileAndRunJavaCode(sourceCode);
            }
        });
    }
}
