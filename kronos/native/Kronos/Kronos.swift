import Foundation

@objc public class Kronos : NSObject {

    @objc(sync::) public class func sync(pool: String, timeIntervalInMilliSeconds: Int64) {
        let firstCallback: ((Date, TimeInterval) -> Void)? = { (date, offset) in
            print("➡️ Kronos: first sync: \(date) offset: \(offset)")
        }
        let completionCallback: ((Date?, TimeInterval?) -> Void)? = { (date, offset) in
            print("⬅️ Kronos: completion sync: \(date) offset: \(offset)")
        }
        Clock.sync(from: pool, timeIntervalInSeconds: Double(timeIntervalInMilliSeconds/1000), first: firstCallback, completion: completionCallback)
    }
    
    @objc(now) public class func now() -> Date? {
        return Clock.now
    }

    @objc(blockingSync::) public class func blockingSync(pool: String, timeIntervalInMilliSeconds: Int64) {
        let semaphore = DispatchSemaphore(value: 0)
        let firstCallback: ((Date, TimeInterval) -> Void)? = { (date, offset) in
            print("➡️ Kronos: first sync: \(date) offset: \(offset)")
        }
        let completionCallback: ((Date?, TimeInterval?) -> Void)? = { (date, offset) in
            print("⬅️ Kronos: completion sync: \(date) offset: \(offset)")
            semaphore.signal()
        }
        Clock.sync(from: pool, timeIntervalInSeconds: Double(timeIntervalInMilliSeconds/1000), first: firstCallback, completion: completionCallback)
        semaphore.wait()
    }
    
    @objc(syncWithCallback:::) public class func sync(pool: String, timeIntervalInMilliSeconds: Int64, callback: @escaping (Date?, NSNumber?) -> Void) {
        let firstCallback: ((Date, TimeInterval) -> Void)? = { (date, offset) in
            print("➡️ Kronos: first sync: \(date) offset: \(offset)")
        }
        let completionCallback: ((Date?, TimeInterval?) -> Void)? = { (date, offset) in
            print("⬅️ Kronos: completion sync: \(date) offset: \(offset)")
            let intervalNumber = offset.map(NSNumber.init(value:)) as NSNumber?
            callback(date, intervalNumber)
        }
        Clock.sync(from: pool, timeIntervalInSeconds: Double(timeIntervalInMilliSeconds/1000), first: firstCallback, completion: completionCallback)
    }
}
