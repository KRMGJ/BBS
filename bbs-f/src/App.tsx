/* eslint-disable @typescript-eslint/no-explicit-any */
import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './views/users/Login'
import Layout from './layout/Layout'
import Join from './views/users/Join'
import NttList from './views/ntt/NttList'
import NttForm from './views/ntt/NttForm'
import { useLoginUserStore } from './hooks/useLoginUser'
import { useEffect } from 'react'
import { apis, endpoints } from './apis/api'

function App() {

  const { user, setLoginUser, resetLoginUser } = useLoginUserStore();
  const path = location.pathname;
  
  useEffect(() => {
    if (user) return;
    async function fetchMe() {
      try {
        const res = await apis.get<any>(endpoints.me);
        if (res.isLogin === true) {
          setLoginUser(res.data);
        } else {
          resetLoginUser();
        }
      } catch (err) {
        console.error(err);
      }
    }
    fetchMe();
  }, [path, resetLoginUser, setLoginUser, user])

  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path='/bbs/user'>
          <Route path="loginView.do" element={<Login />} />
          <Route path="joinView.do" element={<Join />} />
        </Route>

        <Route path='/bbs/notice'>
          <Route path="list.do" element={<NttList />} />
          <Route path="form.do" element={<NttForm />} />
          <Route path="form.do/:nttId" element={<NttForm />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default App
