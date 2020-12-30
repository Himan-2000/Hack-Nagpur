package com.example.myhealth;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Report_adapter extends RecyclerView.Adapter<Report_adapter.ReportViewHolder> {

    Context context;
    int r_n=0,p_n=0;
    String[] reports_t = new String[100];
    String[] reports_f = new String[100];
    String[] reports_d = new String[100];


    public Report_adapter(Context context, int r_n, int p_n, String[] reports_t, String[] reports_f, String[] reports_d) {
        this.context = context;
        this.r_n = r_n;
        this.p_n = p_n;
        this.reports_t = reports_t;
        this.reports_f = reports_f;
        this.reports_d = reports_d;

    }



    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from( context );
        View view = inflater.inflate( R.layout.list_view_report,null );
        ReportViewHolder holder = new ReportViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {

        String h = "Report: "+reports_t[position];
        String p = "Download Link: "+reports_f[position];
        String t = "Dated: "+reports_d[position];
        holder.head.setText( h );
        holder.link.setText( p );
        holder.time.setText( t );
    }

    @Override
    public int getItemCount() {
        return r_n;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder
    {

        TextView head,link,time;

        public ReportViewHolder(@NonNull View itemView) {
            super( itemView );

            head = itemView.findViewById( R.id.title_head );
            link = itemView.findViewById( R.id.file_link );
            link.setMovementMethod( LinkMovementMethod.getInstance() );
            time = itemView.findViewById( R.id.date_disp );




        }
    }

}
