// Generated by view binder compiler. Do not edit!
package com.example.plantapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.plantapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RvPlantsItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView healthemoji;

  @NonNull
  public final ImageView ivPlant;

  @NonNull
  public final ConstraintLayout rvContainer;

  @NonNull
  public final TextView tvHealth;

  @NonNull
  public final TextView tvPlantTitle;

  @NonNull
  public final TextView tvPlantType;

  private RvPlantsItemBinding(@NonNull CardView rootView, @NonNull TextView healthemoji,
      @NonNull ImageView ivPlant, @NonNull ConstraintLayout rvContainer, @NonNull TextView tvHealth,
      @NonNull TextView tvPlantTitle, @NonNull TextView tvPlantType) {
    this.rootView = rootView;
    this.healthemoji = healthemoji;
    this.ivPlant = ivPlant;
    this.rvContainer = rvContainer;
    this.tvHealth = tvHealth;
    this.tvPlantTitle = tvPlantTitle;
    this.tvPlantType = tvPlantType;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static RvPlantsItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RvPlantsItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.rv_plants_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RvPlantsItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.healthemoji;
      TextView healthemoji = ViewBindings.findChildViewById(rootView, id);
      if (healthemoji == null) {
        break missingId;
      }

      id = R.id.ivPlant;
      ImageView ivPlant = ViewBindings.findChildViewById(rootView, id);
      if (ivPlant == null) {
        break missingId;
      }

      id = R.id.rv_container;
      ConstraintLayout rvContainer = ViewBindings.findChildViewById(rootView, id);
      if (rvContainer == null) {
        break missingId;
      }

      id = R.id.tvHealth;
      TextView tvHealth = ViewBindings.findChildViewById(rootView, id);
      if (tvHealth == null) {
        break missingId;
      }

      id = R.id.tvPlantTitle;
      TextView tvPlantTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvPlantTitle == null) {
        break missingId;
      }

      id = R.id.tvPlantType;
      TextView tvPlantType = ViewBindings.findChildViewById(rootView, id);
      if (tvPlantType == null) {
        break missingId;
      }

      return new RvPlantsItemBinding((CardView) rootView, healthemoji, ivPlant, rvContainer,
          tvHealth, tvPlantTitle, tvPlantType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}