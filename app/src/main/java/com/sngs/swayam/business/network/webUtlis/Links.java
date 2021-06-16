package com.sngs.swayam.business.network.webUtlis;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.sngs.swayam.business.R;
import com.sngs.swayam.business.models.State_Model;
import com.sngs.swayam.business.network.model.Area.AreaListDatum;
import com.sngs.swayam.business.network.model.Attribute.AttributeListDatum;
import com.sngs.swayam.business.network.model.Category.CategoryListDatum;
import com.sngs.swayam.business.network.model.City.CityListDatum;
import com.sngs.swayam.business.network.model.Notification.Notification;
import com.sngs.swayam.business.network.model.Packages.PackageDetail;
import com.sngs.swayam.business.network.model.Packages.PackageList;
import com.sngs.swayam.business.network.model.PromoDetail.PromotionListResult;
import com.sngs.swayam.business.network.model.PromotionBanner.PromotionBannerListResult;
import com.sngs.swayam.business.network.model.RedeemCoin.PromotionDiscountTransactionListResult;
import com.sngs.swayam.business.network.model.ServiceProvider.ServiceListDatum;
import com.sngs.swayam.business.network.model.State.StateListDatum;
import com.sngs.swayam.business.network.model.SubCategory.SubCategoryListDatum;
import com.sngs.swayam.business.network.model.TranscationDetail.TransactionListResult;

import java.util.ArrayList;
import java.util.List;

public class Links {

   // public  static final String SERVER_URL = "https://www.digisysindiatech.com/swayam/app/API/";
    public  static final String SERVER_URL = "https://sohumngs.com/swayam/app/API/";
    public  static final String User_Type = "4";


    //Login api
    public  static  final String Customer_Sign_In = "signIn.php";
    public  static  final String Mobile_Verify = "mobileVerify.php";
    public  static  final String Customer_Sign_Up = "customerSignUp.php";

    public  static  final String Customer_Profile = "customerProfileDetail.php";
    public  static  final String Get_Customer_Detail = "getCustomerDetail.php";

    //Cutsomer detail
    public  static  final String Customer_Business_Detail = "customerBusinessDetail.php";
    public  static  final String Get_Service_Provider_List = "getServiceList.php";
    public  static  final String Get_Category_List = "getCategoryList.php";
    public  static  final String Get_Sub_Category_List = "getSubCategoryList.php";
    public  static  final String Get_Attribute_List = "getAttributeList.php";
    public  static  final String Customer_Uploaded_File = "customerDetail.php";

    //Get state, city , area api
    public  static  final String Get_State_List = "getStateList.php";
    public  static  final String Get_City_List = "getCityList.php";
    public  static  final String Get_Area_List = "getAreaList.php";

    //banner api
    public  static  final String Add_Promotion = "publishPromotion.php";
    public  static  final String getCustomer_Promotion_BannerList = "getCustomerPromotionBannerList.php";

    // Promotion api
    public  static  final String getCustomer_Promotion_DetailList = "getCustomerPromotionDetailList.php";

    //trancation detail
    public  static  final String getCustomer_Transaction_DetailList = "getCustomerTransactionDetailList.php";
    public  static  final String getCustomer_Package_DetailList = "getCustomerPackageHistory.php";

    //edit profile
    public  static  final String Edit_Profile = "editCustomerProfile.php";

    //package
    public  static  final String GetPackage_List = "getPackageList.php";
    public  static  final String Save_Customer_Package = "saveCustomerPackage.php";

    //Notification
    public  static  final String Get_Notification_List = "getNotificationList.php";
    public  static  final String Delete_Notification = "deleteNotification.php";
    public  static  final String Promotion_QueryReply = "promotionQueryReply.php";

    //contact us
    public  static  final String Contact_US = "contactUsQuery.php";

    //user smart detail
    public  static  final String Get_User_Detail = "getUserDetail.php";

    //Promotaion Purchase
    public  static  final String Promotion_Purchase_Discount = "promotionPurchaseDiscount.php";
    public  static  final String CustomerPaymentMethod = "promotionPurchaseDiscountPaymentMethod.php";
    public  static  final String Get_CustomerPromotion_DiscountTransactionList = "getCustomerPromotionDiscountTransactionList.php";

    public static List<String> services_provider = new ArrayList<>();
    public static List<State_Model> select_state = new ArrayList<>();
    public static List<State_Model> select_city = new ArrayList<>();
    public static List<State_Model> select_service_area = new ArrayList<>();
    public static List<String> notification_list = new ArrayList<>();
    public static  ArrayList<String> selected_image_array_list = new ArrayList<String>();

    public static List<ServiceListDatum> service_list = new ArrayList<>();
    public static List<String> service_sp_list = new ArrayList<>();

    public static List<CategoryListDatum> category_list = new ArrayList<>();
    public static List<String> category_sp_list = new ArrayList<>();

    public static List<SubCategoryListDatum> sub_category_list = new ArrayList<>();
    public static List<String> sub_category_sp_list = new ArrayList<>();

    public static List<AttributeListDatum> attribute_list = new ArrayList<>();
    public static List<String> Banner_list = new ArrayList<>();
    public static  List<String> selected_category_ids_list = new ArrayList<String>();

    public static  List<StateListDatum> state_list = new ArrayList<>();
    public static  List<CityListDatum> city_list = new ArrayList<>();
    public static  List<AreaListDatum> area_list = new ArrayList<>();


    //Promote yourself
    public static List<String> Days_list = new ArrayList<>();
    public static List<String> Range_list = new ArrayList<>();
    public static List<String> Promissing_visiblity_list = new ArrayList<>();
    public static boolean is_Promissing_visiblity = false;

    public static String selected_state_name = "";
    public static String selected_state_id = "";

    public static String selected_city_name = "";
    public static String selected_city_id = "";

    public static String selected_servies_name = "";
    public static String selected_servies_id = "";

    public static String selected_role = "";

    public static String selected_service_id = "";
    public static String selected_category_id = "";

    public static boolean is_agree = false;


    //Promotion Banner
    public static List<PromotionBannerListResult> PromotionBannerList = new ArrayList<>();
    public static List<PromotionListResult> PromotionList_Result = new ArrayList<>();


    //transcation Detail
    public static List<com.sngs.swayam.business.network.model.CustomerPackages.PackageList> Package_list= new ArrayList<>();
    public static List<PromotionDiscountTransactionListResult> TransactionList= new ArrayList<>();

    //packge Detail
    public static List<PackageList> PackageList_Result = new ArrayList<>();
    public static List<PackageDetail> PackageList_detail_Result = new ArrayList<>();
    public static List<PackageDetail> Temp_PackageList_detail_Result = new ArrayList<>();
    public static boolean packgae_status = false;
    public static int selected_pk_id = 0;

    //notification
    public static List<Notification> notification_List = new ArrayList<>();

    public static class Header {
        public static final String Auth_ID = "authId";
        public static final String Auth_Token = "authToken";
        public static final String User_Type = "userType";
        public static final String Reg_Id = "regId";
        public static final String Device_Id = "device_id";
    }

    public static class MobileVerifyDetail {
        public static final String User_Type = "userType";
        public static final String Contact_Number = "contactNumber";
    }

    public static class CustomerSignUpDetail {
        public static final String User_Type = "userType";
        public static final String Customer_Type = "customerType";
        public static final String Customer_Name = "customerName";
        public static final String Customer_Contact_Number = "customerContactNumber";
        public static final String Customer_Employee_Code = "customerEmployeeCode";
    }

    public static class CustomerProfileDetail {
        public static final String CustomerShopType = "customerShopType";
        public static final String CustomerShopName = "customerShopName";
        public static final String Customer_Contact_Number = "customerAlternateContactNumber";
        public static final String Customer_Email = "customerEmail";
        public static final String Customer_Shop_Address1 = "customerShopAddress1";
        public static final String Customer_Shop_Address2 = "customerShopAddress2";
        public static final String Customer_Shop_State_Id = "customerShopStateId";
        public static final String Customer_Shop_State = "customerShopState";
        public static final String Customer_Shop_City_Id = "customerShopCityId";
        public static final String Customer_Shop_City = "customerShopCity";
        public static final String Customer_Shop_Area_ID = "customerShopAreaId";
        public static final String Customer_Shop_Area = "customerShopArea";
        public static final String Customer_Shop_Pincode = "customerShopPincode";
        public static final String Customer_Profile_Picture = "customerProfilePicture";
        public static final String Customer_Shop_Country = "customerShopCountry";
    }

    public static class CustomerBusinessInfoDetail {
        public static final String customer_Shop_WorkingDays = "customerShopWorkingDays";
        public static final String customer_Shop_StartTime = "customerShopStartTime";
        public static final String customer_Shop_EndTime = "customerShopEndTime";
        public static final String customer_shop_LunchStartTime = "customerShopLunchStartTime";
        public static final String customer_shop_LunchEndTime = "customerShopLunchEndTime";
        public static final String customer_Shop_Year = "customerShopYear";
        public static final String customer_Shop_TotalEmployee = "customerShopTotalEmployee";
        public static final String customer_Shop_Website = "customerShopWebsite";
        public static final String customer_Shop_About = "customerShopAbout";
    }

    public static class GetCategoryListDetail {
        public static final String Service_Id = "serviceId";
    }

    public static class GetSubCategoryListDetail {
        public static final String Service_Id = "serviceId";
        public static final String Category_Id = "categoryId";
        public static final String Filter_Type = "isFilter";
    }

    public static class CategoryUploadFileDetail {
        public static final String Service_Id = "serviceId";
        public static final String Category_Id = "categoryId";
        public static final String Sub_Category_Id = "subCategoryId";
        public static final String Attribute_Id = "attributeId";
        public static final String Attachment1 = "attachment1";
        public static final String Attachment2 = "attachment2";
        public static final String Attachment3 = "attachment3";
        public static final String Attachment4 = "attachment4";
    }

    public static class Customer_Sign_In_Detail {
        public static final String User_Contact_Number = "userContactNumber";
    }

    public static class City_List_Detail {
        public static final String state_Id = "stateId";
    }

    public static class Area_List_Detail {
        public static final String state_Id = "stateId";
        public static final String city_Id= "cityId";
    }


    public static class Edit_List_Detail {
        public static final String customer_Name = "customerName";
        public static final String customer_ContactNumber= "customerContactNumber";
        public static final String customer_ProfilePicture= "customerProfilePicture";
    }

    public static class Service_Detail {
        public static final String Filter_Type = "isFilter";
    }

    public static class AddPromotion {
        public static final String promotionTitle = "promotionTitle";
        public static final String serviceId = "serviceId";
        public static final String categoryId = "categoryId";
        public static final String subCategoryId = "subCategoryId";
        public static final String promotionStartDate = "promotionStartDate";
        public static final String promotionDaysLimit = "promotionDaysLimit";
        public static final String promotionPrice = "promotionPrice";
        public static final String promotionAdditionalOffer = "promotionAdditionalOffer";
        public static final String promotionVisibility = "promotionVisibility";
        public static final String promotionDescription = "promotionDescription";
        public static final String promotionAttechment = "promotionAttechment";
        public static final String promotionBannerId = "promotionBannerId";
        public static final String promotionVisibilityMobile = "promotionVisibilityMobile";
        public static final String promotionFinalRate = "promotionFinalRate";
    }

    public static class Notificationview{
        public static final String notificationId = "notificationId";
    }

    public static class Promotionview{
        public static final String promotionStatus = "promotionStatus";
    }

    public static class Promotion_QueryReplyview{
        public static final String promotion_QueryId = "promotionQueryId";
        public static final String query_Replay = "queryReplay";
    }


    public static class ViewPromotion {
        public static final String Promotion_Id = "promotionId";
    }

    public static class PromotionDetail {
        public static final String Customer_Id = "customerId";
    }

    public static class SaveCustomerPackageDetail {
        public static final String PackageDetail_Id = "packageDetailId";
    }

    public static class ContactUSDetail {
        public static final String Name = "name";
        public static final String Contact_Number = "contactNumber";
        public static final String Email = "email";
        public static final String Message = "message";
    }

    public static class SmartUserDetail {
        public static final String Contact_Number = "userMobileNumber";
    }

    public static class PromotionPurchaseDiscount {
        public static final String User_Id = "userId";
        public static final String User_ContactNumber = "userContactNumber";
        public static final String User_Otp = "userOtp";
        public static final String Promotion_DiscountAmount = "promotionDiscountAmount";
        public static final String Customer_ContactNumber = "customerContactNumber";
        public static final String Purchase_DiscountId = "promotionPurchaseDiscountId";
        public static final String Payment_Method = "paymentMethod";
        public static final String Item_Name = "itemName";
        public static final String Item_Purchase_Price = "itemPurchasePrice";
        public static final String Item_Selling_Price = "itemSellingPrice";
    }

    public static void snack_bar(Context context, RelativeLayout main_layout, String mes){
        TSnackbar snackbar = TSnackbar.make(main_layout, ""+mes, TSnackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}
