package com.html_parser.service.vo;

import java.io.Serializable;

public class PrintInfo implements Serializable {
		
		private static final long serialVersionUID = 8357898345647697059L;
		
		private String url;
		private String type;		// 01 : html 태그 제거, 02 : text 전체
		private int groupCnt;
		
		public String getUrl() {
				return url;
		}
		
		public void setUrl( String url ) {
				this.url = url;
		}
		
		public String getType() {
				return type;
		}
		
		public void setType( String type ) {
				this.type = type;
		}
		
		public int getGroupCnt() {
				return groupCnt;
		}
		
		public void setGroupCnt( int groupCnt ) {
				this.groupCnt = groupCnt;
		}
		
		@Override
		public String toString() {
				return "PrintInfo{" +
								   "url='" + url + '\'' +
								   ", type='" + type + '\'' +
								   ", groupCnt=" + groupCnt +
								   '}';
		}
}
