package assignment4;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object representation of Tweets
 * You should not need to modify this class
 * If you feel like you do so, ask on piazza before proceeding
 */

public class Tweets
    {
        private int Id;

        @JsonProperty("Id")
        public int getId() { return this.Id; }

        public void setId(int Id) { this.Id = Id; }

        private String Name;
        @JsonProperty("Name")
        public String getName() { return this.Name; }

        public void setName(String Name) { this.Name = Name; }

        private String Date;
        @JsonProperty("Date")
        public String getDate() { return this.Date; }

        public void setDate(String Date) { this.Date = Date; }

        private String Text;
        @JsonProperty("Text")
        public String getText() { return this.Text; }

        public void setText(String Text) { this.Text = Text; }

        public Tweets(){}

        @Override public String toString() {
            return "(" + this.getId()
                    + " " + this.getName()
                    + " " + this.getDate()
                    + ") " + this.getText();
        }
    }


