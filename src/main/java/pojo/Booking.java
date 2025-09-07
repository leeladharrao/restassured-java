package pojo;

public class Booking {

    private int bookingid;
    private BookingDetails bookingDetails;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public BookingDetails getBookingDetails() {
        return bookingDetails;
    }

    public void setBooking(BookingDetails booking) {
        this.bookingDetails = booking;
    }
}
