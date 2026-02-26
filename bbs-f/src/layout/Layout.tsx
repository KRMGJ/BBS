import Header from './Header'
import Footer from './Footer'
import { Outlet } from 'react-router-dom'

export default function Layout() {
    return (
        <div id="wrap">
            <Header />
            <main id="container">
                <div className="container-inner">
                    <Outlet />
                </div>
            </main>
            <Footer />
        </div>
    )
}
