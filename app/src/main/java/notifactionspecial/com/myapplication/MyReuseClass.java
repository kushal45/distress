package notifactionspecial.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 08-Nov-15.
 */
public class MyReuseClass  implements Parcelable {


String image;
       String titles;
        String description;

    MyReuseClass(String titles, String description) {
            this.titles = titles;
            this.description = description;
        }
    MyReuseClass(String titles, String description,String  image) {
        this.titles = titles;
        this.description = description;
        this.image=image;
    }
    public MyReuseClass(Parcel in) {
        titles = in.readString();
        description = in.readString();
    }
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Description",description);
            obj.put("Title", titles);
            if(image!=null)
            {
                obj.put("image",image);
            }


        } catch (JSONException e) {

        }
        return obj;
    }

    public static final Parcelable.Creator<MyReuseClass> CREATOR = new Parcelable.Creator<MyReuseClass>() {
        @Override
        public MyReuseClass createFromParcel(Parcel in) {
            return new MyReuseClass(in);
        }

        @Override
        public MyReuseClass[] newArray(int size) {
            return new MyReuseClass[size];
        }
    };

    public  String getName() {
            return titles;
        }
    public  String getImage() {
        return image;
    }

        public  String getAge() {
            return description;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titles);
        dest.writeString(description);
    }
}


