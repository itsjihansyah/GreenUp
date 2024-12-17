package com.example.plantapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class MyPlantDetailDirections {
  private MyPlantDetailDirections() {
  }

  @NonNull
  public static ActionMyPlantDetailToUpdatePlantFragment actionMyPlantDetailToUpdatePlantFragment(
      @NonNull String id, @NonNull String name, @NonNull String type) {
    return new ActionMyPlantDetailToUpdatePlantFragment(id, name, type);
  }

  @NonNull
  public static NavDirections actionMyPlantDetailToPlantHomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_myPlantDetail_to_plantHomeFragment);
  }

  public static class ActionMyPlantDetailToUpdatePlantFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionMyPlantDetailToUpdatePlantFragment(@NonNull String id, @NonNull String name,
        @NonNull String type) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      if (name == null) {
        throw new IllegalArgumentException("Argument \"name\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("name", name);
      if (type == null) {
        throw new IllegalArgumentException("Argument \"type\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("type", type);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionMyPlantDetailToUpdatePlantFragment setId(@NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionMyPlantDetailToUpdatePlantFragment setName(@NonNull String name) {
      if (name == null) {
        throw new IllegalArgumentException("Argument \"name\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("name", name);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionMyPlantDetailToUpdatePlantFragment setType(@NonNull String type) {
      if (type == null) {
        throw new IllegalArgumentException("Argument \"type\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("type", type);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("id")) {
        String id = (String) arguments.get("id");
        __result.putString("id", id);
      }
      if (arguments.containsKey("name")) {
        String name = (String) arguments.get("name");
        __result.putString("name", name);
      }
      if (arguments.containsKey("type")) {
        String type = (String) arguments.get("type");
        __result.putString("type", type);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_myPlantDetail_to_updatePlantFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getId() {
      return (String) arguments.get("id");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getName() {
      return (String) arguments.get("name");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getType() {
      return (String) arguments.get("type");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionMyPlantDetailToUpdatePlantFragment that = (ActionMyPlantDetailToUpdatePlantFragment) object;
      if (arguments.containsKey("id") != that.arguments.containsKey("id")) {
        return false;
      }
      if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
        return false;
      }
      if (arguments.containsKey("name") != that.arguments.containsKey("name")) {
        return false;
      }
      if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
        return false;
      }
      if (arguments.containsKey("type") != that.arguments.containsKey("type")) {
        return false;
      }
      if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getId() != null ? getId().hashCode() : 0);
      result = 31 * result + (getName() != null ? getName().hashCode() : 0);
      result = 31 * result + (getType() != null ? getType().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionMyPlantDetailToUpdatePlantFragment(actionId=" + getActionId() + "){"
          + "id=" + getId()
          + ", name=" + getName()
          + ", type=" + getType()
          + "}";
    }
  }
}
