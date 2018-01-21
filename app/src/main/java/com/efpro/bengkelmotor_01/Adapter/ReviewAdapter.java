package com.efpro.bengkelmotor_01.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.efpro.bengkelmotor_01.Model.Bengkel;
import com.efpro.bengkelmotor_01.R;
import com.efpro.bengkelmotor_01.Model.ReviewBengkel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzapalupi on 12/13/2017.
 */

public class ReviewAdapter extends ArrayAdapter<ReviewBengkel> {

//    public ReviewAdapter(@NonNull Context context,  @NonNull List<ReviewBengkel> objects) {
//        super(context, 0, objects);
//    }
    int status;
    Uri photoUrl;
    ArrayList<Bengkel> myReviewedBengkels;

    //Constructor untuk review adapter pada DetailBengkelActivity
    public ReviewAdapter(@NonNull Context context,  @NonNull List<ReviewBengkel> objects, int status) {
        super(context, 0, objects);
        this.status = status;
    }

    //Constructor untuk review adapter pada MyReviewFragment
    // TODO: 12/19/2017 tambahkan parameter untuk foto bengkel yg telah direview (MyReviewFragment) OK
    public ReviewAdapter(@NonNull Context context, @NonNull List<ReviewBengkel> objects, @NonNull ArrayList<Bengkel> myReviewedBengkels, int status) {
        super(context, 0, objects );
        this.status = status;
        this.myReviewedBengkels = myReviewedBengkels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_list, parent, false);
        }

        final ImageView imgProfile    = (ImageView) convertView.findViewById(R.id.imgProfile);
        TextView txtUsername    = (TextView) convertView.findViewById(R.id.txtUsername);
        TextView txtDate        = (TextView) convertView.findViewById(R.id.txtDate);
        TextView txtComment     = (TextView) convertView.findViewById(R.id.txtComment);
        RatingBar rtbUserRate   = (RatingBar) convertView.findViewById(R.id.rtbUserRate);

        try {
            ReviewBengkel reviewBengkel = getItem(position);
            txtUsername.setText(reviewBengkel.getUsername());
            txtDate.setText(reviewBengkel.getDate());
            txtComment.setText(reviewBengkel.getComment());
            rtbUserRate.setRating(reviewBengkel.getRate());
            photoUrl = Uri.parse(reviewBengkel.getPhotoUrl());
            Glide.with((Activity) getContext()).asBitmap().load(photoUrl)
                    .into(new BitmapImageViewTarget(imgProfile) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable rounded =
                                    RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                            rounded.setCircular(true);
                            imgProfile.setImageDrawable(rounded);
                        }
                    });

            //Tampilan pada MyReviewFragment
            if(status == 1){
                imgProfile.setVisibility(View.GONE);
                Bengkel bengkel = myReviewedBengkels.get(position);
                txtUsername.setText(bengkel.getbNama());
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
