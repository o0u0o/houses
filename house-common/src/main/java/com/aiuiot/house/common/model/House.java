package com.aiuiot.house.common.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 
 * @Title: House.java
 * @Package com.aiuiot.house.common.model
 * @Description: House的model（entity）
 * @author: ZerOneth
 * @date: 2019年6月21日 上午9:04:14
 * @version V1.0
 */
public class House {
	private Long id; 		// id
	private Integer type; 	// 类型
	private Integer price; 	// 价格
	private String name; 	// 名字
	private String images; 	// 图片
	private Integer area;	// 面积
	private Integer beds;	// 卧室数量
	private Integer baths; 	// 卫生间数量
	private Double rating;	 // 评分

	private String remarks; 	// 描述
	private String properties; 	// 属性信息
	private String floorPlan; 	// 户型图
	private String tags; 		// 标签
	private Date createTime; 	// 创建时间
	private Integer cityId; 	// 城市ID
	private Integer communityId;// 社区（小区）ID
	private String address; 	// 地址
	private String firstImg; 	// 首图
	private List<String> imageList = Lists.newArrayList();	//
	private List<String> floorPlanList = Lists.newArrayList();
	private  List<String> featureList = Lists.newArrayList();



	private List<MultipartFile> houseFiles;

	private List<MultipartFile> floorPlanFiles;
	private Long userId; 		// 拥有盖房屋的用户ID
	private boolean bookmarked;	// 是否收藏
	private Integer state;		// 状态
	private List<Long> ids;
	
	private String sort = "time_desc";	//排序

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
		if(!Strings.isNullOrEmpty(images)) {
			List<String> list = Splitter.on(",").splitToList(images);
			if(!list.isEmpty()) {	//不为空，设置头图
				this.firstImg = list.get(0);
				this.imageList = list; 
			}
		}
		
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getBeds() {
		return beds;
	}

	public void setBeds(Integer beds) {
		this.beds = beds;
	}

	public Integer getBaths() {
		return baths;
	}

	public void setBaths(Integer baths) {
		this.baths = baths;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getFloorPlan() {
		return floorPlan;
	}

	public void setFloorPlan(String floorPlan) {
		this.floorPlan = floorPlan;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstImg() {
		return firstImg;
	}

	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public List<String> getFloorPlanList() {
		return floorPlanList;
	}

	public void setFloorPlanList(List<String> floorPlanList) {
		this.floorPlanList = floorPlanList;
	}

	public List<MultipartFile> getHouseFiles() {
		return houseFiles;
	}

	public void setHouseFiles(List<MultipartFile> houseFiles) {
		this.houseFiles = houseFiles;
	}

	public List<MultipartFile> getFloorPlanFiles() {
		return floorPlanFiles;
	}

	public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
		this.floorPlanFiles = floorPlanFiles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isBookmarked() {
		return bookmarked;
	}

	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<String> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<String> featureList) {
		this.featureList = featureList;
	}

	@Override
	public String toString() {
		return "House{" +
				"id=" + id +
				", type=" + type +
				", price=" + price +
				", name='" + name + '\'' +
				", images='" + images + '\'' +
				", area=" + area +
				", beds=" + beds +
				", baths=" + baths +
				", rating=" + rating +
				", remarks='" + remarks + '\'' +
				", properties='" + properties + '\'' +
				", floorPlan='" + floorPlan + '\'' +
				", tags='" + tags + '\'' +
				", createTime=" + createTime +
				", cityId=" + cityId +
				", communityId=" + communityId +
				", address='" + address + '\'' +
				", firstImg='" + firstImg + '\'' +
				", imageList=" + imageList +
				", floorPlanList=" + floorPlanList +
				", featureList=" + featureList +
				", houseFiles=" + houseFiles +
				", floorPlanFiles=" + floorPlanFiles +
				", userId=" + userId +
				", bookmarked=" + bookmarked +
				", state=" + state +
				", ids=" + ids +
				", sort='" + sort + '\'' +
				'}';
	}
}
