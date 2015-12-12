package com.gjiazhe.nipponcolors;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjiazhe.ggprogressbar.GGArcProgressBar;
import com.gjiazhe.ggprogressbar.GGVerticalProgressBar;
import com.gjiazhe.nipponcolors.model.NipponColor;

import java.util.List;

/**
 * Created by gjz on 12/12/15.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private Context mContext;
    private List<NipponColor> mColorList;
    private onItemClickListener mOnItemClickListener;

    public ColorAdapter(Context context, List<NipponColor> colorList) {
        mContext = context;
        mColorList = colorList;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColorViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.color_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ColorViewHolder holder, int position) {
        NipponColor nipponColor = mColorList.get(position);
        String rgb = '#' + nipponColor.getRgb();
        int color = Color.parseColor(rgb);
        holder.vColor.setBackgroundColor(color);
        holder.tvId.setTextColor(color);
        holder.tvId.setText(nipponColor.getId());
        holder.tvName_jp.setText(nipponColor.getNameJA());
        holder.tvName_en.setText(nipponColor.getNameEN());
        holder.tvRGB_16.setText(rgb);

        holder.bar_r.resetProgress();
        holder.bar_r.setProgress(Color.red(color));
        holder.bar_g.resetProgress();
        holder.bar_g.setProgress(Color.green(color));
        holder.bar_b.resetProgress();
        holder.bar_b.setProgress(Color.blue(color));

        holder.ring_c.resetProgress();
        holder.ring_c.setProgress(nipponColor.getCmyk_c());
        holder.ring_m.resetProgress();
        holder.ring_m.setProgress(nipponColor.getCmyk_m());
        holder.ring_y.resetProgress();
        holder.ring_y.setProgress(nipponColor.getCmyk_y());
        holder.ring_k.resetProgress();
        holder.ring_k.setProgress(nipponColor.getCmyk_k());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mColorList.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        View vColor;
        TextView tvId;
        TextView tvName_jp;
        TextView tvName_en;
        TextView tvRGB_16;
        GGArcProgressBar ring_c;
        GGArcProgressBar ring_m;
        GGArcProgressBar ring_y;
        GGArcProgressBar ring_k;
        GGVerticalProgressBar bar_r;
        GGVerticalProgressBar bar_g;
        GGVerticalProgressBar bar_b;
        public ColorViewHolder(View itemView) {
            super(itemView);
            vColor = itemView.findViewById(R.id.color);
            tvId = (TextView) itemView.findViewById(R.id.color_id);
            tvName_jp = (TextView) itemView.findViewById(R.id.color_name_ja);
            tvName_en = (TextView) itemView.findViewById(R.id.color_name_en);
            tvRGB_16 = (TextView) itemView.findViewById(R.id.rgb_16);
            ring_c = (GGArcProgressBar) itemView.findViewById(R.id.c_ring);
            ring_m = (GGArcProgressBar) itemView.findViewById(R.id.m_ring);
            ring_y = (GGArcProgressBar) itemView.findViewById(R.id.y_ring);
            ring_k = (GGArcProgressBar) itemView.findViewById(R.id.k_ring);
            bar_r = (GGVerticalProgressBar) itemView.findViewById(R.id.bar_r);
            bar_g = (GGVerticalProgressBar) itemView.findViewById(R.id.bar_g);
            bar_b = (GGVerticalProgressBar) itemView.findViewById(R.id.bar_b);
        }
    }
}
