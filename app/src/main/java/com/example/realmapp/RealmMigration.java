package com.example.realmapp;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration {


    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            RealmObjectSchema contactSchema = schema.get("Contact");

            // Combine 'firstName' and 'lastName' in a new field called 'fullName'
            contactSchema
                    .addField("nameNumber", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("nameNumber", obj.getString("name") + " " + obj.getString("numero"));
                        }
                    })
                    .removeField("name")
                    .removeField("numero");
            oldVersion++;
        }

    }
}
