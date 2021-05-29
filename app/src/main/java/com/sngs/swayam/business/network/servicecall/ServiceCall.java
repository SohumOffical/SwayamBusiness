package com.sngs.swayam.business.network.servicecall;

import android.content.Context;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.sngs.swayam.business.network.HashMapLog;
import com.sngs.swayam.business.network.model.Notification.NotificationBaseResponse;
import com.sngs.swayam.business.network.model.Packages.GetPackageListBaseResponse;
import com.sngs.swayam.business.network.model.PromoDetail.PromoDetailBaseResponse;
import com.sngs.swayam.business.network.model.PromotionBanner.PromotionBannerBaseResponse;
import com.sngs.swayam.business.network.model.PromotionDetail.GetPromotionDetailBaseResponse;
import com.sngs.swayam.business.network.model.PromotionList.GetCustomerPromotionListBaseResponse;
import com.sngs.swayam.business.network.model.TranscationDetail.TranscationDetailBaseResponse;
import com.sngs.swayam.business.network.model.UserDetail.UserDetailBaseResponse;
import com.sngs.swayam.business.network.webUtlis.Links;
import com.sngs.swayam.business.network.apiinterface.APIInterface;
import com.sngs.swayam.business.network.model.Area.GetAreaListBaseResponse;
import com.sngs.swayam.business.network.model.Attribute.GetAttributeListBaseResponse;
import com.sngs.swayam.business.network.model.BaseResponse;
import com.sngs.swayam.business.network.model.Category.GetCategoryListBaseResponse;
import com.sngs.swayam.business.network.model.City.GetCityListBaseResponse;
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse;
import com.sngs.swayam.business.network.model.CustomerSignUp.CustomerSignUpBaseResponse;
import com.sngs.swayam.business.network.model.Login.LoginBaseResponse;
import com.sngs.swayam.business.network.model.MobileVerify.MobileVerifyBaseResponse;
import com.sngs.swayam.business.network.model.ServiceProvider.GetServiceProviderBaseResponse;
import com.sngs.swayam.business.network.model.State.GetStateListBaseResponse;
import com.sngs.swayam.business.network.model.SubCategory.GetSubCategoryListBaseResponse;
import com.sngs.swayam.business.network.remotes.APIClient;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ServiceCall extends AppCompatActivity {


    //Mobile Verify
    public static Call<MobileVerifyBaseResponse> callMobileVerify(Context context, String user_type, String contact_number) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.MobileVerifyDetail.User_Type,user_type);
        mBodyMap.put(Links.MobileVerifyDetail.Contact_Number,contact_number);

        HashMapLog.getHashMapLog("callMobileVerify", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postMobileVerify(mBodyMap);
    }

    //Customer SignUp
    public static Call<CustomerSignUpBaseResponse> callCustomerSignUp(Context context,String device_id, String fcm_token,
                                                                      String user_type, String customer_type,
                                                                      String customer_name, String customer_contact_number,
                                                                      String customer_emp_code) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Device_Id, device_id);
        mBodyMap.put(Links.Header.Reg_Id,fcm_token);
        mBodyMap.put(Links.CustomerSignUpDetail.User_Type,user_type);
        mBodyMap.put(Links.CustomerSignUpDetail.Customer_Type,customer_type);
        mBodyMap.put(Links.CustomerSignUpDetail.Customer_Name,customer_name);
        mBodyMap.put(Links.CustomerSignUpDetail.Customer_Contact_Number,customer_contact_number);
        mBodyMap.put(Links.CustomerSignUpDetail.Customer_Employee_Code,customer_emp_code);

        HashMapLog.getHashMapLog("callCustomerSignUp", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postCustomerSignUp(mBodyMap);
    }

    //Customer Profile Detail
    public static Call<BaseResponse> callCustomerProfileDetail(Context context,String auth_id, String auth_token, String user_type,
                                                               String Customer_Shop_Type,
                                                               String Customer_Shop_Name,
                                                               String Customer_Contact_Number,
                                                              String Customer_Email, String Customer_Shop_Address1,
                                                              String Customer_Shop_Address2,
                                                              String Customer_Shop_State_id,String Customer_Shop_State,
                                                               String Customer_Shop_City_id,String Customer_Shop_City,
                                                               String Customer_Shop_Area_id, String Customer_Shop_Area,
                                                              String Customer_Shop_Pincode, File Image_file,
                                                              String Customer_Shop_Country) {


        MediaType mediaType = MediaType.parse("multipart/form-data");
        MultipartBody.Part send_image_File = null;

        if(Image_file!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,Image_file);
            send_image_File = MultipartBody.Part.createFormData(Links.CustomerProfileDetail.Customer_Profile_Picture,
                    Uri.fromFile(Image_file).toString(), requestBody);
        }

        RequestBody authid = RequestBody.create(MediaType.parse("text/plain"), auth_id);
        RequestBody authtoken = RequestBody.create(MediaType.parse("text/plain"), auth_token);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plain"), user_type);
        RequestBody CustomerShopType = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Type);
        RequestBody CustomerShopName = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Name);
        RequestBody CustomerContactNumber = RequestBody.create(MediaType.parse("text/plain"), Customer_Contact_Number);
        RequestBody CustomerEmail = RequestBody.create(MediaType.parse("text/plain"), Customer_Email);
        RequestBody CustomerShopAddress1 = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Address1);
        RequestBody CustomerShopAddress2 = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Address2);

        RequestBody Customer_Shop_Stateid = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_State_id);
        RequestBody CustomerShopState = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_State);

        RequestBody Customer_Shop_Cityid = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_City_id);
        RequestBody CustomerShopCity = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_City);

        RequestBody Customer_Shop_Areaid = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Area_id);
        RequestBody CustomerShopArea = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Area);

        RequestBody CustomerShopPincode = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Pincode);
        RequestBody CustomerShopCountry = RequestBody.create(MediaType.parse("text/plain"), Customer_Shop_Country);

        return APIClient.getClient().create(APIInterface.class).postCustomerProfileDetail(authid, authtoken, usertype, CustomerShopType, CustomerShopName, CustomerContactNumber,
                CustomerEmail, CustomerShopAddress1, CustomerShopAddress2,Customer_Shop_Stateid, CustomerShopState,
                Customer_Shop_Cityid, CustomerShopCity,Customer_Shop_Areaid, CustomerShopArea, CustomerShopPincode,
                send_image_File, CustomerShopCountry);
    }

    //Customer Business Detail
    public static Call<BaseResponse> callCustomerBusinessDetail(Context context, String auth_id, String auth_token, String user_type, String customer_shop_workingDays,
                                                                      String customer_shop_startTime, String customer_shop_endTime, String customer_shop_lunchstartTime,String customer_shop_lunchendTime,
                                                                String customer_shop_year, String customer_shop_totalEmployee, String customer_shop_website,
                                                                String customer_shop_about) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_WorkingDays,customer_shop_workingDays);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_StartTime,customer_shop_startTime);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_EndTime,customer_shop_endTime);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_shop_LunchStartTime,customer_shop_lunchstartTime);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_shop_LunchEndTime,customer_shop_lunchendTime);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_Year,customer_shop_year);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_TotalEmployee,customer_shop_totalEmployee);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_Website,customer_shop_website);
        mBodyMap.put(Links.CustomerBusinessInfoDetail.customer_Shop_About,customer_shop_about);

        HashMapLog.getHashMapLog("callCustomerBusinessDetail", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postCustomerBusinessDetail(mBodyMap);
    }

    //Service Provider List
    public static Call<GetServiceProviderBaseResponse> callGetServiceProviderList(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetServiceProviderList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetServiceProviderList(mBodyMap);
    }

    //Get Category List
    public static Call<GetCategoryListBaseResponse> callGetCategoryList(Context context, String auth_id, String auth_token, String user_type,
                                                                        String service_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.GetCategoryListDetail.Service_Id,service_id);

        HashMapLog.getHashMapLog("callGetCategoryList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetCategoryList(mBodyMap);
    }

    //Get Sub Category List
    public static Call<GetSubCategoryListBaseResponse> callGetSubCategoryList(Context context, String auth_id, String auth_token, String user_type,
                                                                              String service_id, String category_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.GetSubCategoryListDetail.Service_Id,service_id);
        mBodyMap.put(Links.GetSubCategoryListDetail.Category_Id,category_id);

        HashMapLog.getHashMapLog("callGetSubCategoryList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetSubCategoryList(mBodyMap);
    }

    //Get Attribute List
    public static Call<GetAttributeListBaseResponse> callGetAttributeList(Context context,
                                                                          String auth_id, String auth_token,
                                                                          String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetAttributeList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetAttributeListt(mBodyMap);
    }

    //Upload File
    public static Call<BaseResponse> callUploadFileDetail(Context context,String auth_id, String auth_token,
                                                          String user_type, String service_id,
                                                          String category_id, String sub_category_id,
                                                          String attribute_id, File Image_file,
                                                          File Image_file2, File Image_file3,
                                                          File Image_file4) {


        MediaType mediaType = MediaType.parse("multipart/form-data");
        MultipartBody.Part send_image_File = null;
        MultipartBody.Part send_image_File2 = null;
        MultipartBody.Part send_image_File3 = null;
        MultipartBody.Part send_image_File4 = null;

        if(Image_file!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,Image_file);
            send_image_File = MultipartBody.Part.createFormData(Links.CategoryUploadFileDetail.Attachment1,
                    Uri.fromFile(Image_file).toString(), requestBody);
        }


        if(Image_file2!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,Image_file2);
            send_image_File2 = MultipartBody.Part.createFormData(Links.CategoryUploadFileDetail.Attachment2,
                    Uri.fromFile(Image_file2).toString(), requestBody);
        }

        if(Image_file3!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,Image_file3);
            send_image_File3 = MultipartBody.Part.createFormData(Links.CategoryUploadFileDetail.Attachment3,
                    Uri.fromFile(Image_file3).toString(), requestBody);
        }

        if(Image_file4!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,Image_file4);
            send_image_File4 = MultipartBody.Part.createFormData(Links.CategoryUploadFileDetail.Attachment4,
                    Uri.fromFile(Image_file4).toString(), requestBody);
        }

        RequestBody authid = RequestBody.create(MediaType.parse("text/plain"), auth_id);
        RequestBody authtoken = RequestBody.create(MediaType.parse("text/plain"), auth_token);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plain"), user_type);
        RequestBody serviceid = RequestBody.create(MediaType.parse("text/plain"), service_id);
        RequestBody categoryid = RequestBody.create(MediaType.parse("text/plain"), category_id);
        RequestBody subcategoryid = RequestBody.create(MediaType.parse("text/plain"), sub_category_id);
        RequestBody attributeid = RequestBody.create(MediaType.parse("text/plain"), attribute_id);

        return APIClient.getClient().create(APIInterface.class).postCustomerUploadFile(authid, authtoken, usertype,
                serviceid, categoryid, subcategoryid, attributeid, send_image_File,
                send_image_File2, send_image_File3,send_image_File4);
    }

    //Customer Sign In
    public static Call<LoginBaseResponse> callCustomerSignIn(Context context, String device_id, String fcm_token,
                                                             String contact_num, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Device_Id, device_id);
        mBodyMap.put(Links.Header.Reg_Id,fcm_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Customer_Sign_In_Detail.User_Contact_Number,contact_num);

        HashMapLog.getHashMapLog("callCustomerSignIn", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postCustomerSignIn(mBodyMap);
    }


    //Customer Detail
    public static Call<CustomerDetailBaseResponse> callCustomerDetail(Context context, String auth_id,
                                                                      String auth_token,
                                                                      String user_type,
                                                                      String customerId) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.PromotionDetail.Customer_Id,customerId);


        HashMapLog.getHashMapLog("callCustomerDetail", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postCustomerDetail(mBodyMap);
    }

    //Swayam Smart
    public static Call<BaseResponse> callGetCustomerPromotionList(Context context, String auth_id, String auth_token,
                                                                  String user_Type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_Type);

        HashMapLog.getHashMapLog("callGetCustomerPromotionList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetCustomerPromotionList(mBodyMap);
    }

    //Get State List
    public static Call<GetStateListBaseResponse> callGetStateList(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetStateList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetStateList(mBodyMap);
    }

    //Get City List
    public static Call<GetCityListBaseResponse> callGetCityList(Context context, String auth_id, String auth_token, String user_type,
                                                                String state_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.City_List_Detail.state_Id,state_id);

        HashMapLog.getHashMapLog("callGetCityList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetCityList(mBodyMap);
    }

    //Get City List
    public static Call<GetAreaListBaseResponse> callGetAreaList(Context context, String auth_id, String auth_token, String user_type,
                                                                String state_id, String city_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Area_List_Detail.state_Id,state_id);
        mBodyMap.put(Links.Area_List_Detail.city_Id,city_id);

        HashMapLog.getHashMapLog("callGetAreaList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetAreaList(mBodyMap);
    }


    //banner api
    public static Call<PromotionBannerBaseResponse> callgetCustomerPromotionList(Context context, String auth_id,
                                                                                 String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callgetCustomerPromotionList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postgetCustomerPromotion(mBodyMap);
    }


    //Add Promotion
    public static Call<BaseResponse> callAddPromotion(Context context,String auth_id,String auth_token,
                                                      String user_type, String promotion_Title,
                                                      String service_Id,String category_Id,String subCategory_Id,
                                                      String promotion_StartDate, String promotion_DaysLimit,
                                                      String promotion_Price, String promotion_AdditionalOffer,
                                                      String promotion_Visibility, String promotion_Description,
                                                      String promotion_Attechment,String promotionBanner_Id,
                                                      String promotionVisibility_Mobile,
                                                      String promotionFinal_Rate) {

        RequestBody authid = RequestBody.create(MediaType.parse("text/plain"), auth_id);
        RequestBody authtoken = RequestBody.create(MediaType.parse("text/plain"), auth_token);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plain"), user_type);
        RequestBody promotionTitle = RequestBody.create(MediaType.parse("text/plain"), promotion_Title);
        RequestBody serviceId = RequestBody.create(MediaType.parse("text/plain"), service_Id);
        RequestBody categoryId = RequestBody.create(MediaType.parse("text/plain"), category_Id);
        RequestBody subCategoryId = RequestBody.create(MediaType.parse("text/plain"), subCategory_Id);
        RequestBody promotionStartDate = RequestBody.create(MediaType.parse("text/plain"), promotion_StartDate);
        RequestBody promotionDaysLimit = RequestBody.create(MediaType.parse("text/plain"), promotion_DaysLimit);
        RequestBody promotionPrice = RequestBody.create(MediaType.parse("text/plain"), promotion_Price);
        RequestBody promotionAdditionalOffer = RequestBody.create(MediaType.parse("text/plain"), promotion_AdditionalOffer);
        RequestBody promotionVisibility = RequestBody.create(MediaType.parse("text/plain"), promotion_Visibility);
        RequestBody promotionDescription = RequestBody.create(MediaType.parse("text/plain"), promotion_Description);
        RequestBody promotionAttechment = RequestBody.create(MediaType.parse("text/plain"), promotion_Attechment);
        RequestBody promotionBannerId = RequestBody.create(MediaType.parse("text/plain"), promotionBanner_Id);
        RequestBody promotionVisibilityMobile = RequestBody.create(MediaType.parse("text/plain"), promotionVisibility_Mobile);
        RequestBody promotionFinalRate = RequestBody.create(MediaType.parse("text/plain"), promotionFinal_Rate);

        return APIClient.getClient().create(APIInterface.class).postAddPromotion(authid, authtoken,usertype,
                promotionTitle,serviceId,categoryId, subCategoryId, promotionStartDate, promotionDaysLimit,
                promotionPrice, promotionAdditionalOffer, promotionVisibility, promotionDescription,
                promotionAttechment,promotionBannerId,promotionVisibilityMobile,promotionFinalRate);
    }


    //banner api
    public static Call<PromoDetailBaseResponse> calGetCustomerPromotionDetailList(Context context, String auth_id,
                                                                                  String auth_token, String user_type,
                                                                                  String promotion_Status) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Promotionview.promotionStatus,promotion_Status);

        HashMapLog.getHashMapLog("calGetCustomerPromotionDetailList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetCustomerPromotionDetail(mBodyMap);
    }



    //transcation Detail
    public static Call<TranscationDetailBaseResponse> callCustomerTransactionDetailList(
            Context context, String auth_id,
            String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("calGetCustomerPromotionList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postgetCustomer_Transaction_DetailList(mBodyMap);
    }



    //Add Promotion
    public static Call<BaseResponse> callEditProfile(Context context,String auth_id,String auth_token,
                                                      String user_type, String name,
                                                     String number,File Image_file) {

        MediaType mediaType = MediaType.parse("multipart/form-data");
        MultipartBody.Part send_image_File = null;

        if(Image_file!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,Image_file);
            send_image_File = MultipartBody.Part.createFormData(Links.Edit_List_Detail.customer_ProfilePicture,
                    Uri.fromFile(Image_file).toString(), requestBody);
        }

        RequestBody authid = RequestBody.create(MediaType.parse("text/plain"), auth_id);
        RequestBody authtoken = RequestBody.create(MediaType.parse("text/plain"), auth_token);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plain"), user_type);
        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody user_number = RequestBody.create(MediaType.parse("text/plain"), number);


        return APIClient.getClient().create(APIInterface.class).postEditProfile(
                authid,
                authtoken,
                usertype,
                user_name,
                user_number,
                send_image_File);
    }

    //packages
    public static Call<GetPackageListBaseResponse> callGetPackageList(Context context, String auth_id, String auth_token,
                                                                      String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetPackageList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetPackageList(mBodyMap);
    }

    public static Call<BaseResponse> callSaveCustomerPackage(Context context, String auth_id, String auth_token,
                                                                      String user_type,String product_detail_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.SaveCustomerPackageDetail.PackageDetail_Id,product_detail_id);

        HashMapLog.getHashMapLog("callGetPackageList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postSaveCustomerPackage(mBodyMap);
    }


    public static Call<NotificationBaseResponse> callGetNotificationList(Context context, String auth_id, String auth_token,
                                                                         String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetNotificationList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetNotificationList(mBodyMap);
    }

    public static Call<BaseResponse> callDeleteNotification(Context context, String auth_id,
                                                                        String auth_token, String user_type,
                                                                        String delete_notification_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Notificationview.notificationId,delete_notification_id);

        HashMapLog.getHashMapLog("callDeleteNotification", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postDelete_Notification(mBodyMap);
    }

    public static Call<BaseResponse> callPromotionQueryReply(Context context, String auth_id,
                                                            String auth_token, String user_type,
                                                            String promotion_QueryId,String query_Replay) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Promotion_QueryReplyview.promotion_QueryId,promotion_QueryId);
        mBodyMap.put(Links.Promotion_QueryReplyview.query_Replay,query_Replay);

        HashMapLog.getHashMapLog("callDeleteNotification", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postPromotionQueryReply_Notification(mBodyMap);
    }

    public static Call<BaseResponse> callContactUS(Context context, String auth_id, String auth_token,
                                                                         String user_type,String name,
                                                   String mobile_no,String email,String message) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.ContactUSDetail.Name,name);
        mBodyMap.put(Links.ContactUSDetail.Contact_Number,mobile_no);
        mBodyMap.put(Links.ContactUSDetail.Email,email);
        mBodyMap.put(Links.ContactUSDetail.Message,message);

        HashMapLog.getHashMapLog("callContactUS", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postContactUS(mBodyMap);
    }

    //Smart User Detail List
    public static Call<UserDetailBaseResponse> callUserDetail(Context context, String auth_id, String auth_token, String user_type, String contact_Number) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.SmartUserDetail.Contact_Number,contact_Number);

        HashMapLog.getHashMapLog("callUserDetail", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postUserDetail(mBodyMap);
    }

    public static Call<BaseResponse> callPromotionPurchaseDiscount(Context context, String auth_id, String auth_token,
                                                                   String user_type,String user_Id,
                                                                   String user_ContactNumber,String user_Otp,String promotion_DiscountAmount,
                                                                   String customer_ContactNumber) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.PromotionPurchaseDiscount.User_Id,user_Id);
        mBodyMap.put(Links.PromotionPurchaseDiscount.User_ContactNumber,user_ContactNumber);
        mBodyMap.put(Links.PromotionPurchaseDiscount.User_Otp,user_Otp);
        mBodyMap.put(Links.PromotionPurchaseDiscount.Promotion_DiscountAmount,promotion_DiscountAmount);
        mBodyMap.put(Links.PromotionPurchaseDiscount.Customer_ContactNumber,customer_ContactNumber);

        HashMapLog.getHashMapLog("callPromotionPurchaseDiscount", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postPromotionPurchaseDiscount(mBodyMap);
    }

}

