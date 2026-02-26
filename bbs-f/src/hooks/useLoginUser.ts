import { create } from "zustand";
import { persist } from "zustand/middleware";

type User = {
    userId: string;
    name: string;
    email: string;
}

type AuthState = {
    user: User | null;
    lastSyncedAt: number;
    setLoginUser: (u: User) => void;
    resetLoginUser: () => void;
    setSyncedNow: () => void;
    _hydrated: boolean;
    setHydrated: (v: boolean) => void;
};

export const useLoginUserStore = create<AuthState>()(
    persist(
        (set) => ({
            user: null,
            lastSyncedAt: 0,
            setLoginUser: (u) => set({ user: u }),
            resetLoginUser: () => set({ user: null, lastSyncedAt: 0 }),
            setSyncedNow: () => set({ lastSyncedAt: Date.now() }),
            _hydrated: false,
            setHydrated: (v) => set({ _hydrated: v }),
        }),
        {
            name: "login-user",
            partialize: (s) => ({ user: s.user, lastSyncedAt: s.lastSyncedAt }),
            onRehydrateStorage: () => (state) => state?.setHydrated(true),
        }
    )
);