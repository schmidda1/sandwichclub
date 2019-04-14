package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich = new Sandwich();
    ImageView mIngredientsIv;
    TextView mOriginTv;
    TextView mDescriptionTv;
    TextView mIngredientsTv;
    TextView mAlsoKnownAsTv;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mIngredientsIv = findViewById(R.id.image_iv);
        mOriginTv = findViewById(R.id.origin_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);

        intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        //Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            //original code has sandwich as null
        }
        catch(Exception e){
            closeOnError();
            e.printStackTrace();
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsIv);

        setTitle(sandwich.getMainName());
        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.back_to_main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back_to_main) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void populateUI() {
        String originText = sandwich.getPlaceOfOrigin();
        if (originText.equals(""))
            originText = "Unknown";
        mOriginTv.setText(originText);
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.isEmpty())
            alsoKnownAsList.add("Unknown");
        mAlsoKnownAsTv.setText("");
        for (int i = 0; i < alsoKnownAsList.size(); ++i)
            if (i == alsoKnownAsList.size() - 1)
                mAlsoKnownAsTv.append(alsoKnownAsList.get(i) + ".");
            else
                mAlsoKnownAsTv.append(alsoKnownAsList.get(i) + ", ");
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty())
            ingredientsList.add("Unknown");
        mIngredientsTv.setText("");
        for (int i = 0; i < ingredientsList.size(); ++i)
            if (i == ingredientsList.size() - 1)
                mIngredientsTv.append(ingredientsList.get(i) + ".");
            else
                mIngredientsTv.append(ingredientsList.get(i) + ", ");
        String descriptionText = sandwich.getDescription();
        if (descriptionText.equals(""))
            descriptionText = "Unknown";
        mDescriptionTv.setText(descriptionText);

    }
}
