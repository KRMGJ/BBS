import { create } from "zustand";
import { persist } from "zustand/middleware";

type User = {
    uniqId: string;
    name: string;
    email: string;
}

type AuthState = {
    user: User | null;
    setLoginUser: (u: User) => void;
    resetLoginUser: () => void;
    checkUser: () => void;
};

export const useLoginUserStore = create<AuthState>()(
    persist(
        (set) => ({
            user: null,
            setLoginUser: (u) => set({ user: u }),
            resetLoginUser: () => set({ user: null }),
            checkUser: () => {
                const { user } = useLoginUserStore.getState();
                if (!user) {
                    alert("로그인이 필요한 기능입니다.");
                    return;
                }
            }
        }),
        {
            name: "login-user"
        }
    )
);