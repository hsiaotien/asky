package pojo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Author implements Comparable{
    private String name;
    private int age;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * HashCodeBuilder
     * @return
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,"age","address");
    }

    /**
     * EqualsBuilder
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj,"name","address");
    }

    /**
     * ToStringBuilder
     * @return
     */
    public String toString000() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String toString001() {
        //同toString01类似，但更简洁
        return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
    }

    /**
     * TOStringBuilder + ToStringStyle
     * @return
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }

    public String toString01() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }


    public String toString02() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }

    public String toString03() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }

    public String toString04() {
        return new ToStringBuilder(this, ToStringStyle.NO_FIELD_NAMES_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }

    public String toString05() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }

    public String toString06() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("name",name)
                .append("age",age)
                .append("address",address)
                .toString();
    }

    public int compareTo(Object o) {
        return 0;
    }
}
