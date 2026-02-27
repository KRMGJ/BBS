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
};

export const useLoginUserStore = create<AuthState>()(
    persist(
        (set) => ({
            user: null,
            setLoginUser: (u) => set({ user: u }),
            resetLoginUser: () => set({ user: null }),
        }),
        {
            name: "login-user"
        }
    )
);