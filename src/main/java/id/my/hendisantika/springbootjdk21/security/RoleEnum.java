package id.my.hendisantika.springbootjdk21.security;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 24/08/25
 * Time: 08.36
 * To change this template use File | Settings | File Templates.
 */
public enum RoleEnum {
    ADMIN(1),
    USER(2),
    SIGNED_OUT(3);

    private final int roleId;

    RoleEnum(int roleId) {
        this.roleId = roleId;
    }

    public static RoleEnum fromRoleId(int roleId) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getRoleId() == roleId) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role ID: " + roleId);
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return name();
    }
}
