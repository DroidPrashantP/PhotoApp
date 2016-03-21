package com.app.camera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.app.camera.R;
import com.app.camera.adapters.FontGridViewAdapter;
import com.app.camera.utils.Constants;
import com.app.camera.utils.widget.ColorPickerDialog;

public class TextViewActivity extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener {

    private EditText mInputText;
    private TextView mPreviewText;
    private GridView mFontGridView;
    private int mSelectedColor = 0xFF000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        findIds();
    }

    private void findIds() {
        mInputText = (EditText) findViewById(R.id.inputeditText);
        mPreviewText = (TextView) findViewById(R.id.previewText);
        mFontGridView = (GridView) findViewById(R.id.fontGridView);

        FontGridViewAdapter fontGridViewAdapter = new FontGridViewAdapter(this);
        mFontGridView.setAdapter(fontGridViewAdapter);

        mInputText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mPreviewText.setText(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public void picColor(View view) {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, this, "Color", R.color.primary, R.color.blue);
        colorPickerDialog.show();

    }

    public void ApplyText(View view) {
        Intent intent = new Intent();
        intent.putExtra(Constants.Bundle.TEXT, mInputText.getText().toString());
        intent.putExtra(Constants.Bundle.COLOR_CODE,mSelectedColor);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorChanged(String key, int color) {
        mPreviewText.setTextColor(color);
        mSelectedColor = color;
    }

}
