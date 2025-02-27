import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 10,  // 가상 사용자 수
    duration: '30s', // 테스트 실행 시간
};

let payload = JSON.stringify({
    id: '1',
});

let params = {
    headers: {
        'Content-Type': 'application/json',
    },
};

export default function () {'

    let res = http.post('http://localhost:8080/user/get_my_balance',payload,params); // 테스트할 API 주소 입력
    check(res, { 'status was 200': (r) => r.status === 200 });
    sleep(1); // 1초 대기
}