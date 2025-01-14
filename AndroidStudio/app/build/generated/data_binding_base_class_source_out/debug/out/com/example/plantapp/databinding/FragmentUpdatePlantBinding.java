// Generated by view binder compiler. Do not edit!
package com.example.plantapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.plantapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentUpdatePlantBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView detailPlantImage;

  @NonNull
  public final TextInputLayout fillName;

  @NonNull
  public final TextInputLayout fillType;

  @NonNull
  public final Button saveButton;

  @NonNull
  public final ToolbarBinding toolbar;

  @NonNull
  public final TextInputEditText updatePlantName;

  @NonNull
  public final TextInputEditText updatePlantType;

  private FragmentUpdatePlantBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView detailPlantImage, @NonNull TextInputLayout fillName,
      @NonNull TextInputLayout fillType, @NonNull Button saveButton,
      @NonNull ToolbarBinding toolbar, @NonNull TextInputEditText updatePlantName,
      @NonNull TextInputEditText updatePlantType) {
    this.rootView = rootView;
    this.detailPlantImage = detailPlantImage;
    this.fillName = fillName;
    this.fillType = fillType;
    this.saveButton = saveButton;
    this.toolbar = toolbar;
    this.updatePlantName = updatePlantName;
    this.updatePlantType = updatePlantType;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentUpdatePlantBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentUpdatePlantBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_update_plant, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentUpdatePlantBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.detail_plant_image;
      ImageView detailPlantImage = ViewBindings.findChildViewById(rootView, id);
      if (detailPlantImage == null) {
        break missingId;
      }

      id = R.id.fill_name;
      TextInputLayout fillName = ViewBindings.findChildViewById(rootView, id);
      if (fillName == null) {
        break missingId;
      }

      id = R.id.fill_type;
      TextInputLayout fillType = ViewBindings.findChildViewById(rootView, id);
      if (fillType == null) {
        break missingId;
      }

      id = R.id.saveButton;
      Button saveButton = ViewBindings.findChildViewById(rootView, id);
      if (saveButton == null) {
        break missingId;
      }

      id = R.id.toolbar;
      View toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }
      ToolbarBinding binding_toolbar = ToolbarBinding.bind(toolbar);

      id = R.id.updatePlantName;
      TextInputEditText updatePlantName = ViewBindings.findChildViewById(rootView, id);
      if (updatePlantName == null) {
        break missingId;
      }

      id = R.id.updatePlantType;
      TextInputEditText updatePlantType = ViewBindings.findChildViewById(rootView, id);
      if (updatePlantType == null) {
        break missingId;
      }

      return new FragmentUpdatePlantBinding((LinearLayout) rootView, detailPlantImage, fillName,
          fillType, saveButton, binding_toolbar, updatePlantName, updatePlantType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
