import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return menu when restaurant exists"
    request {
        method GET()
        url("/restaurants/96bf508e-245a-4423-8f6d-22f46472e02d")
    }
    response {
        status 200
        body(
                id: "96bf508e-245a-4423-8f6d-22f46472e02d",
                name: "test",
                menu: []
        )
    }
}