package com.example.exenote.Adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.exenote.DB.Note;
import com.example.exenote.Fragments.RecordingFragment;
import com.example.exenote.NoteViewModel;
import com.example.exenote.R;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private NoteViewModel noteViewModel;


    public NoteListAdapter(List<Note> noteList, FragmentManager fragmentManager, NoteViewModel noteViewModel) {
        this.noteViewModel = noteViewModel;
        this.noteList = noteList;
        this.fragmentManager = fragmentManager;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.note_items, parent, false);


        return new NoteViewHolder(view);
    }


    private void deleteItem(Note note, int position){
        noteViewModel.deleteNote(note);
        position = noteList.indexOf(note);
        if (position != -1) {
            noteList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Note> notes) {
        this.noteList = notes;
        notifyDataSetChanged(); // Уведомить RecyclerView об изменении данных
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Note note = noteList.get(position);

            holder.titleView.setText(note.getNoteTitle());
            holder.descriptionView.setText(note.getNoteDescription());

            Glide.with(holder.deleteImage.getContext())
                    .asDrawable()
                    .load(R.drawable.trashbin_icon_png)
                    .into(holder.deleteImage);

            holder.deleteImage.setOnClickListener((View v) -> {
                try {
                    Glide.with(holder.deleteImage.getContext())
                            .asGif()
                            .load(R.drawable.trashbin_delete)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .listener(new RequestListener<GifDrawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<GifDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(@NonNull GifDrawable resource, @NonNull Object model, Target<GifDrawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                                    resource.setLoopCount(1);
                                    resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                                        @Override
                                        public void onAnimationEnd(Drawable drawable) {
                                            deleteItem(note, position);
                                            super.onAnimationEnd(drawable);
                                            //do whatever after specified number of loops complete
                                        }
                                    });
                                    return false;
                                }
                            })
                            .into(holder.deleteImage);
                } catch (Exception e) {
                    System.out.println(e);
                }
            });

            holder.itemView.setOnClickListener((View v) -> {

                RecordingFragment recordingFragment = new RecordingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", noteList.get(position).getNid());
                recordingFragment.setArguments(bundle);

                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, recordingFragment)
                            .commit();
                }
            });

    }

    @Override
    public int getItemCount() {
       return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, descriptionView;
        ImageView deleteImage;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.TextTitle);
            descriptionView = itemView.findViewById(R.id.TextDescription);
            deleteImage = itemView.findViewById(R.id.delete_image);

        }
    }
}
