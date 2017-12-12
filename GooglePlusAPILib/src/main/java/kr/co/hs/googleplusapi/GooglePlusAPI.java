package kr.co.hs.googleplusapi;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.Person;
import java.util.ArrayList;

/**
 * Created by privacydev on 2017. 12. 12..
 */

public class GooglePlusAPI {
    public static Intent loginIntent(Context context){
        return loginIntent(context, null);
    }

    public static Intent loginIntent(Context context, String clientId){
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);
        if(clientId != null)
            builder.requestIdToken(clientId);
        builder.requestProfile();
        builder.requestEmail();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, builder.build());
        return googleSignInClient.getSignInIntent();
    }


    public static Person getPerson(Context context, Intent intent, String appName){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
        if(task.isSuccessful()){
            GoogleSignInAccount googleSignInAccount = task.getResult();
            return getPerson(context, googleSignInAccount.getEmail(), appName);
        }else
            return null;
    }


    public static Person getPerson(Context context, GoogleSignInAccount googleSignInAccount, String appName){
        return getPerson(context, googleSignInAccount.getEmail(), appName);
    }

    public static Person getPerson(Context context, String email, String appName){
        ArrayList<String> scopes = new ArrayList();
        scopes.add(Scopes.PROFILE);
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(context, scopes);
        credential.setSelectedAccount(new Account(email, "com.google"));
        People people = new People.Builder(AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(appName)
                .build();

        try {
            Person person = people.people()
                    .get("people/me")
                    .setRequestMaskIncludeField("person.addresses,person.age_ranges,person.biographies,person.birthdays,person.bragging_rights,person.cover_photos,person.email_addresses,person.events,person.genders,person.im_clients,person.interests,person.locales,person.memberships,person.metadata,person.names,person.nicknames,person.occupations,person.organizations,person.phone_numbers,person.photos,person.relations,person.relationship_interests,person.relationship_statuses,person.residences,person.skills,person.taglines,person.urls")
                    .execute();

            return person;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
