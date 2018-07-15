package com.example.ding.umutos.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.ding.umutos.R;
import com.example.ding.umutos.business.AccessWishlists;
import com.example.ding.umutos.objects.Wish;

public class NewWishActivity extends AppCompatActivity {

    private EditText newWishName, newWishAuthor;

    private String userName;

    private String title, author;

    private AccessWishlists accessWishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        userName = getIntent().getStringExtra("userName");
        accessWishList=new AccessWishlists();
    }

    public void buttonBookSubmit(View view) {
        newWishName=(EditText)findViewById(R.id.wishAuthorName);
        newWishAuthor=(EditText)findViewById(R.id.wishBookName);

        title=newWishName.getText().toString();
        author=newWishAuthor.getText().toString();



        if (title.length()<1 || author.length()<1  )
            showDialog();

        else
            showNewDialog();

    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lack of wish information")
                .setMessage("\nPlease enter valid wish book title and author.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {}
                })
                .show();
    }

    private void showNewDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation:")
                .setMessage("\n"+"Sure to add "+title+" as a new wish?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                       // Wish newWish=new Wish();
                       // Book aBook= new Book(title,author,0,detail,category,Double.parseDouble(price),userName);
                        //accessBookList.insertBook(aBook);
                        int userType = 0;
                        Intent intent = new Intent(NewWishActivity.this, WishActivity.class);
                        intent.putExtra("userType", userType);
                        intent.putExtra("userName", userName);

                        startActivity(intent);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                })
                .show();
    }

    public void buttonNewWishCancel(View view) {
        finish();
    }



}
