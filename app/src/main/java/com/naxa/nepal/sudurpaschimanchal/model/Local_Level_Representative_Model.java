package com.naxa.nepal.sudurpaschimanchal.model;

/**
 * Created by User on 11/4/2016.
 */

public class Local_Level_Representative_Model {

    public String mThumbnail;
    public String _name_en;
    public String _name_np;
    public String _palika_name_en;
    public String _palika_name_np;
    public String _number;

    public Local_Level_Representative_Model(String _name_en, String _palika_name_en, String _number) {
        this._name_en = _name_en;
        this._palika_name_en = _palika_name_en;
        this._number = _number;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String get_name_en() {
        return _name_en;
    }

    public void set_name_en(String _name_en) {
        this._name_en = _name_en;
    }

    public String get_name_np() {
        return _name_np;
    }

    public void set_name_np(String _name_np) {
        this._name_np = _name_np;
    }

    public String get_palika_name_en() {
        return _palika_name_en;
    }

    public void set_palika_name_en(String _palika_name_en) {
        this._palika_name_en = _palika_name_en;
    }

    public String get_palika_name_np() {
        return _palika_name_np;
    }

    public void set_palika_name_np(String _palika_name_np) {
        this._palika_name_np = _palika_name_np;
    }

    public String get_number() {
        return _number;
    }

    public void set_number(String _number) {
        this._number = _number;
    }
}
