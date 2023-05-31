package com.example.alahamar.retrofit;

import androidx.browser.customtabs.CustomTabsService;

import com.example.alahamar.Response.LoginModel;
import com.example.alahamar.apimodel.AboutTeamsModel;
import com.example.alahamar.apimodel.ChangePasswordModel;
import com.example.alahamar.apimodel.ClearAllModel;
import com.example.alahamar.apimodel.CommentModel;
import com.example.alahamar.apimodel.CommunityDetailsModel;
import com.example.alahamar.apimodel.CommunityLIkeModel;
import com.example.alahamar.apimodel.ContactUsModel;
import com.example.alahamar.apimodel.EditProfileModel;
import com.example.alahamar.apimodel.EventDetailsModel;
import com.example.alahamar.apimodel.ForgetPasswordModel;
import com.example.alahamar.apimodel.FormsListModel;
import com.example.alahamar.apimodel.LogoutModel;
import com.example.alahamar.apimodel.NotificationModel;
import com.example.alahamar.apimodel.RegisterModel;
import com.example.alahamar.apimodel.TotalCommentsModel;
import com.example.alahamar.apimodel.TotalNotificationModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface Api {

    //
    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> LOGIN_MODEL_CALL(
            @Field("email") String username,
            @Field("password") String password);


    @Multipart
    @POST("user-register")
    Call<RegisterModel> REGISTER_MODEL_CALL(@Part("username") RequestBody username,
                                            @Part("email") RequestBody email,
                                            @Part("phone_number") RequestBody phone_Number,
                                            @Part("password") RequestBody password,
                                            @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("user-rest-password")
    Call<ForgetPasswordModel> FORGET_PASSWORD_MODEL_CALL(@Field("phone_number") String phone_number,
                                                         @Field("new_password") String new_password);


    @FormUrlEncoded
    @POST("contact-inquiry")
    Call<ContactUsModel> CONTACT_US_MODEL_CALL(@Field("name") String name,
                                               @Field("email") String email, @Field("phone_number") String phone_number,
                                               @Field("message") String message);


    @FormUrlEncoded
    @POST("changepassword")
    Call<ChangePasswordModel> CHANGE_PASSWORD_MODEL_CALL(@Header("authorization") String authorization,
                                                         @Field("old_password") String old_password,
                                                         @Field("new_password") String new_password);


    @FormUrlEncoded
    @POST("logout")
    Call<LogoutModel> LOGOUT_MODEL_CALL(@Header("authorization") String authorization,
                                        @Field("refresh") String refresh);


    @GET("event-details")
    Call<EventDetailsModel> eventDetails();

    @GET("community-discussions-deatils")
    Call<CommunityDetailsModel> COMMUNITY_DETAILS_MODEL_CALL(@Header("authorization") String authorization);


    @FormUrlEncoded
    @POST("add-community-comment")
    Call<CommentModel> COMMENT_MODEL_CALL(@Header("authorization") String authorization,
                                          @Field("community_post") String community_post,
                                          @Field("comment") String comment,
                                          @Field("user") String user);


    @GET("user-update-profile")
    Call<EditProfileModel> EDIT_PROFILE_MODEL_CALL(@Header("authorization") String authorization);

    @Multipart
    @PUT("user-update-profile")
    Call<EditProfileModel> EDIT_PROFILE_MODEL_CALL(@Header("authorization")String authorization,
                                                     @Part ("id")RequestBody id,
                                                      @Part("username")RequestBody username,
                                                       @Part("email")RequestBody email,
                                                       @Part("phone_number")RequestBody phone_number,
                                                     @Part MultipartBody.Part image);

    @GET("user-get-notifications")
    Call<NotificationModel> NOTIFICATION_MODEL_CALL (@Header("authorization")String authorization);


    @GET("total-user-notifications")
    Call<TotalNotificationModel>TOTAL_NOTIFICATION_MODEL_CALL (@Header("authorization")String authorization);



@POST("user-clear-all-notifications")
    Call<ClearAllModel> CLEAR_ALL_MODEL_CALL  (@Header("authorization")String authorization);

/*

@FormUrlEncoded
@POST("user-delete-notifications")
*/






@FormUrlEncoded
@POST("community-like")
    Call<CommunityLIkeModel> COMMUNITY_LIKE_MODEL_CALL (@Header("authorization") String authorization,
                                                        @Field("liked") String liked,
                                                        @Field("community") String community,
                                                        @Field("user") String user);


@GET("company-team-members-details")
    Call<AboutTeamsModel> ABOUT_TEAMS_MODEL_CALL();

@GET("forms-lists")
    Call<FormsListModel> FORMS_LIST_MODEL_CALL();


@FormUrlEncoded
    @POST("total-comments")
    Call<TotalCommentsModel> TOTAL_COMMENTS_MODEL_CALL (@Header("authorization") String authorization,
                                                        @Field("id")String id);



}










