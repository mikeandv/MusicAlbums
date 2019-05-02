package com.teenspirit88.musicalbums.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

public class SongList {

        @SerializedName("resultCount")
        @Expose
        private Integer resultCount;
        @SerializedName("results")
        @Expose
        private List<Song> results = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public SongList() {
        }

        /**
         *
         * @param results
         * @param resultCount
         */
        public SongList(Integer resultCount, List<Song> results) {
            super();
            this.resultCount = resultCount;
            this.results = results;
        }

        public Integer getResultCount() {
            return resultCount;
        }

        public void setResultCount(Integer resultCount) {
            this.resultCount = resultCount;
        }

        public List<Song> getResults() {
            return results;
        }

        public void setResults(List<Song> results) {
            this.results = results;
        }

//        @Override
//        public int hashCode() {
//            return new HashCodeBuilder().append(results).append(resultCount).toHashCode();
//        }
//
//        @Override
//        public boolean equals(Object other) {
//            if (other == this) {
//                return true;
//            }
//            if ((other instanceof Example) == false) {
//                return false;
//            }
//            Example rhs = ((Example) other);
//            return new EqualsBuilder().append(results, rhs.results).append(resultCount, rhs.resultCount).isEquals();
//        }
//    }
}
