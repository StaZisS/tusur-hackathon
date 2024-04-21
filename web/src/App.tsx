import {Outlet} from 'react-router-dom';
import {Toaster} from 'sonner';
import {Header} from '@/components/common';
import {Provider} from 'react-redux';
import {store} from '@/store/store.tsx';

const TOASTER_DURATION = 5000;

export const App = () => (
    <Provider store={store}>
        <div className="min-h-screen">
            <Header/>
            <Outlet/>
            <Toaster duration={TOASTER_DURATION}/>
        </div>
    </Provider>
);