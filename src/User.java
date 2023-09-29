public class User <U, P> {
    int userMode = 0;
    private String customer = "63a9f0ea7bb98050796b649e85481845";
    private String restaurant = "6d4b62960a6aa2b1fff43a9c1d95f7b2";

    U user;
    P pass;

    public User(U user, P pass) {
        this.user = user;
        this.pass = pass;
    }

    public int login() {
        if (user.equals("restaurant")) {
            if (pass.equals(restaurant)) return 1;
            else return 0; //Password salah
        } else if (user.equals("user")){
            if (pass.equals(customer)) return 5;
            else return 0; //Password salah
        } else return 6; //User tidak ditemukan
    }
}
