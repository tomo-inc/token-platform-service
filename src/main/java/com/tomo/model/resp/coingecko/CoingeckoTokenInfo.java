//import lombok.Data;
//
//@Data
//public class CoingeckoTokenInfo {
//    private Data data;
//
//    @Data
//    public static class Data {
//        private String id;
//        private String type;
//        private Attributes attributes;
//    }
//
//    @Data
//    public static class Attributes {
//        private String address;
//        private String name;
//        private String symbol;
//        @JsonProperty("image_url")
//        private String imageUrl;
//        @JsonProperty("coingecko_coin_id")
//        private String coingeckoCoinId;
//        private List<String> websites;
//        private String description;
//        @JsonProperty("gt_score")
//        private Double gtScore;
//        @JsonProperty("gt_score_details")
//        private GtScoreDetails gtScoreDetails;
//        @JsonProperty("discord_url")
//        private String discordUrl;
//        @JsonProperty("telegram_handle")
//        private String telegramHandle;
//        @JsonProperty("twitter_handle")
//        private String twitterHandle;
//        private List<String> categories;
//        @JsonProperty("gt_categories_id")
//        private List<String> gtCategoriesId;
//        private Holders holders;
//        @JsonProperty("mint_authority")
//        private String mintAuthority;
//        @JsonProperty("freeze_authority")
//        private String freezeAuthority;
//    }
//
//    @Data
//    public static class GtScoreDetails {
//        private Double pool;
//        private Double transaction;
//        private Double creation;
//        private Double info;
//        private Double holders;
//    }
//
//    @Data
//    public static class Holders {
//        private Integer count;
//        @JsonProperty("distribution_percentage")
//        private DistributionPercentage distributionPercentage;
//        @JsonProperty("last_updated")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
//        private LocalDateTime lastUpdated;
//    }
//
//    @Data
//    public static class DistributionPercentage {
//        @JsonProperty("top_10")
//        private String top10;
//        @JsonProperty("11_30")
//        private String _11_30;
//        @JsonProperty("31_50")
//        private String _31_50;
//        private String rest;
//    }
//}