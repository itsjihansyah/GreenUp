// Generated by view binder compiler. Do not edit!
package com.example.plantapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.plantapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ToolbarBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView deleteIcon;

  @NonNull
  public final ImageView leftIcon;

  @NonNull
  public final TextView toolbarTitle;

  private ToolbarBinding(@NonNull RelativeLayout rootView, @NonNull ImageView deleteIcon,
      @NonNull ImageView leftIcon, @NonNull TextView toolbarTitle) {
    this.rootView = rootView;
    this.deleteIcon = deleteIcon;
    this.leftIcon = leftIcon;
    this.toolbarTitle = toolbarTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ToolbarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ToolbarBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.toolbar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ToolbarBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.delete_icon;
      ImageView deleteIcon = ViewBindings.findChildViewById(rootView, id);
      if (deleteIcon == null) {
        break missingId;
      }

      id = R.id.left_icon;
      ImageView leftIcon = ViewBindings.findChildViewById(rootView, id);
      if (leftIcon == null) {
        break missingId;
      }

      id = R.id.toolbarTitle;
      TextView toolbarTitle = ViewBindings.findChildViewById(rootView, id);
      if (toolbarTitle == null) {
        break missingId;
      }

      return new ToolbarBinding((RelativeLayout) rootView, deleteIcon, leftIcon, toolbarTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}