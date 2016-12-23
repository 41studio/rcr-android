package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class Item implements Serializable {

    private static final long serialVersionUID = -5067848579309200732L;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("times")
    @Expose
    private List<ItemTimes> timesList;

    public Item(int area_id, String name, List<ItemTimes> timesList) {
        this.id = area_id;
        this.name = name;
        this.timesList = timesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemTimes> getTimesList() {
        return timesList;
    }

    public void setTimesList(List<ItemTimes> timesList) {
        this.timesList = timesList;
    }

    public static List<Item> getDummyItem() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1, "Lantai", ItemTimes.getDummyItemTimes()));
        list.add(new Item(2, "Dinding", ItemTimes.getDummyItemTimes()));
        list.add(new Item(3, "Full drain", ItemTimes.getDummyItemTimes()));
        list.add(new Item(4, "Washtafel", ItemTimes.getDummyItemTimes()));
        list.add(new Item(5, "Closet", ItemTimes.getDummyItemTimes()));
        return list;
    }
}


//ActiveRecord::Schema.define(version: 20161213045851) do
//
//        # These are extensions that must be enabled in order to support this database
//        enable_extension "plpgsql"
//
//        create_table "appraisals", force: :cascade do |t|
//        t.integer  "item_time_id"
//        t.integer  "item_id"
//        t.integer  "indicator_id"
//        t.string   "date"
//        t.string   "description"
//        t.datetime "created_at",   null: false
//        t.datetime "updated_at",   null: false
//        t.index ["indicator_id"], name: "index_appraisals_on_indicator_id", using: :btree
//        t.index ["item_id"], name: "index_appraisals_on_item_id", using: :btree
//        t.index ["item_time_id"], name: "index_appraisals_on_item_time_id", using: :btree
//        end
//
//        create_table "areas", force: :cascade do |t|
//        t.integer  "company_id"
//        t.string   "name"
//        t.datetime "created_at", null: false
//        t.datetime "updated_at", null: false
//        t.index ["company_id"], name: "index_areas_on_company_id", using: :btree
//        end
//
//        create_table "companies", force: :cascade do |t|
//        t.string   "name"
//        t.datetime "created_at", null: false
//        t.datetime "updated_at", null: false
//        end
//
//        create_table "indicators", force: :cascade do |t|
//        t.string   "code"
//        t.string   "description"
//        t.datetime "created_at",  null: false
//        t.datetime "updated_at",  null: false
//        end
//
//        create_table "item_times", force: :cascade do |t|
//        t.integer  "item_id"
//        t.string   "time"
//        t.datetime "created_at", null: false
//        t.datetime "updated_at", null: false
//        t.index ["item_id"], name: "index_item_times_on_item_id", using: :btree
//        end
//
//        create_table "items", force: :cascade do |t|
//        t.integer  "id"
//        t.string   "name"
//        t.datetime "created_at", null: false
//        t.datetime "updated_at", null: false
//        t.index ["id"], name: "index_items_on_area_id", using: :btree
//        end
//
//        create_table "users", force: :cascade do |t|
//        t.integer  "company_id"
//        t.string   "email",                  default: "", null: false
//        t.string   "encrypted_password",     default: "", null: false
//        t.string   "reset_password_token"
//        t.datetime "reset_password_sent_at"
//        t.datetime "remember_created_at"
//        t.integer  "sign_in_count",          default: 0,  null: false
//        t.datetime "current_sign_in_at"
//        t.datetime "last_sign_in_at"
//        t.inet     "current_sign_in_ip"
//        t.inet     "last_sign_in_ip"
//        t.string   "confirmation_token"
//        t.datetime "confirmed_at"
//        t.datetime "confirmation_sent_at"
//        t.string   "unconfirmed_email"
//        t.integer  "failed_attempts",        default: 0,  null: false
//        t.string   "unlock_token"
//        t.datetime "locked_at"
//        t.datetime "created_at",                          null: false
//        t.datetime "updated_at",                          null: false
//        t.index ["company_id"], name: "index_users_on_company_id", using: :btree
//        t.index ["email"], name: "index_users_on_email", unique: true, using: :btree
//        t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true, using: :btree
//        end
