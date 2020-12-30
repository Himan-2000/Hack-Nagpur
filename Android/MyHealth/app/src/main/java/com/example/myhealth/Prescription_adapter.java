package com.example.myhealth;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Prescription_adapter extends RecyclerView.Adapter<Prescription_adapter.PrescriptionViewHolder> {

    Context context;
    int r_n=0,p_n=0;

    String[] p_link = new String[100];
    String[] p_doc = new String[100];
    String[] p_date = new String[100];
    String[] p_special = new String[100];

    public Prescription_adapter(View p,Context context, int r_n, int p_n, String[] p_link, String[] p_doc, String[] p_date, String[] p_special) {
        this.context = context;
        this.r_n = r_n;
        this.p_n = p_n;
        this.p_link = p_link;
        this.p_doc = p_doc;
        this.p_date = p_date;
        this.p_special = p_special;
    }



    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from( context );
        View view = inflater.inflate( R.layout.list_view_precription,null );
        PrescriptionViewHolder holder = new PrescriptionViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        String h = "Doctor Name: "+p_doc[position];
        String s = "Speciality: "+p_special[position];
        String p = "Download Link: "+p_link[position];
        String t = "Dated: "+p_date[position];
        holder.head.setText( h );
        holder.special.setText( s );
        holder.link.setText( p );
        holder.time.setText( t );
    }

    @Override
    public int getItemCount() {
        return p_n;
    }

    public class PrescriptionViewHolder extends RecyclerView.ViewHolder
    {

        TextView head,special,link,time;
        ImageButton cart;
        public PrescriptionViewHolder(@NonNull View itemView) {
            super( itemView );

            head = itemView.findViewById( R.id.doctor_head );
            special = itemView.findViewById( R.id.special );
            link = itemView.findViewById( R.id.file_link );
            link.setMovementMethod( LinkMovementMethod.getInstance() );
            time = itemView.findViewById( R.id.date_disp );
            cart = itemView.findViewById( R.id.cart );

            cart.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText( context, "Please wait..", Toast.LENGTH_LONG ).show();
                    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client( okHttpClient )
                            .baseUrl( "https://d045f3257365.ngrok.io/" )
                            .addConverterFactory( GsonConverterFactory.create() )
                            .build();

                    MedicineInterface uai = retrofit.create( MedicineInterface.class );
                    Toast.makeText( context, "Logging your credentials and uploading prescription..", Toast.LENGTH_LONG ).show();
                    Call<MedicineResponse> call = uai.getConfirmation();
                    call.enqueue( new Callback<MedicineResponse>() {
                        @Override
                        public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                            if(response.isSuccessful()){
                                Toast.makeText( context, response.body().getMessage(), Toast.LENGTH_SHORT ).show();
                            }else{
                                Toast.makeText( context, "Response Unsuccessful", Toast.LENGTH_SHORT ).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MedicineResponse> call, Throwable t) {
                            Toast.makeText( context, "Response Failed", Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }
            } );

        }
    }

}
